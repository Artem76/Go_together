package app.service.refbook;

import app.entity.DateFormat;
import app.entity.Refbook;
import app.repository.RefbookRepository;
import app.service.dateFormat.DateFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class RefbookServiceImpl implements RefbookService {
    @Autowired
    private RefbookRepository refbookRepository;

    @Autowired
    private DateFormatService dateFormatService;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Refbook> getRefbookAll() {
        return refbookRepository.findAll();
    }

    @Override
    public List<Refbook> getRefbookAllSortCountry() {
        return refbookRepository.findAllSortCountry();
    }

    @Override
    public List<Refbook> getRefbookAllCountrySort() {
        return refbookRepository.findAllCountrySort();
    }

    @Override
    public List<Refbook> getRefbookAllNetworkSort() {
        return refbookRepository.findAllNetworkSort();
    }

    @Override
    public List<Refbook> getRefbookAllSortMccMnc() {
        return refbookRepository.findAllSortMccMnc();
    }

    @Override
    public Refbook getRefbookById(Long id) {
        return refbookRepository.findOne(id);
    }
    @Override
    public Refbook getRootRefbookByMcc(String mcc) {
        return refbookRepository.findOneByMcc(mcc);
    }

    @Override
    public List<Refbook> getRefbookByMccAndMnc(String mcc, String mnc) {
        return refbookRepository.findByMccAndMnc(mcc, mnc);
    }

    @Override
    public List<Refbook> getRefbookNetworksByMcc(String mcc) {
        return refbookRepository.findNetworksByMcc(mcc);
    }

    @Override
    public List<Refbook> getRefbookDialcodesByMccAndMnc(String mcc, String mnc) {
        return refbookRepository.findAllByMccAndMnc(mcc, mnc);
    }

    @Override
    public List<Map<String, String>> getCountryNetworkList(){
        List<Map<String,String>> result = new ArrayList<>();
        List<Refbook> countryList = refbookRepository.findAllCountrySort();
        for (Refbook country: countryList) {
            Map<String, String> map = new HashMap<>();
            map.put("text", country.getCountry() + "(" + country.getMcc() + ")");
            map.put("mcc_mnc", country.getMcc());
            result.add(map);
            for (Refbook network: new ArrayList<>(refbookRepository.findNetworksByMccSort(country.getMcc()))){
                map = new HashMap<>();
                map.put("text", country.getCountry() + "(" + country.getMcc() + ")" + " - " + network.getNetwork() + "(" + network.getMnc() + ")");
                map.put("mcc_mnc", country.getMcc() + "_" + network.getMnc());
                result.add(map);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean addRefbook(Refbook refbook) {
        refbookRepository.save(refbook);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateRefbook(Long id_refbook, String country, String mcc, String network, String mnc, String dialCode, Integer minLength, Integer maxLength){
        Refbook refbook = refbookRepository.findOne(id_refbook);
        refbook.setCountry(country);
        refbook.setMcc(mcc);
        refbook.setNetwork(network);
        refbook.setMnc(mnc);
        refbook.setDialCode(dialCode);
        refbook.setMinLength(minLength);
        refbook.setMaxLength(maxLength);

        refbookRepository.save(refbook);
        return true;
    }

    @Override
    @Transactional
    public void deleteRefbook(Refbook refbook) {
        List<Refbook> refbooks = new ArrayList<>();
        refbooks.add(refbook);
        if (refbook.getMnc().equals("")){
            refbooks.addAll(refbookRepository.findByMccExceptId(refbook.getMcc(), refbook.getId()));
        }
        refbookRepository.delete(refbooks);
    }

    @Override
    public List<Map<String, String>> getRnHistoryList(Map<String, String> dataMap) {
        List<Map<String, String>> resultList = new ArrayList<>();
        try{
            boolean hasMccmnc = dataMap.get("type").equalsIgnoreCase("mccmnc_price");
            for (int i = 0; i <= Integer.parseInt(dataMap.get("number_last_row")) ; i++){
                Map<String, String> resultMap = new HashMap<>();
                String rowArray[] = dataMap.get("row_" + i).split("_");
                if (hasMccmnc){
                    resultMap.put("mcc", rowArray[0].substring(0, 3));
                    if (Pattern.matches( "^0{1,3}$", rowArray[0].substring(3))){
                        resultMap.put("mnc","0");
                    }else {
                        resultMap.put("mnc", rowArray[0].substring(3).replaceFirst("^0{0,3}", ""));
                    }
                    resultMap.put("rate", rowArray[1].replace(",", "."));
                    resultMap.put("date", discernmentDate(rowArray[2]));
                }else {
                    resultMap.put("mcc", rowArray[0]);
                    if (Pattern.matches( "^0{1,3}$", rowArray[1])){
                        resultMap.put("mnc","0");
                    }else {
                        resultMap.put("mnc", rowArray[1].replaceFirst("^0{0,3}", ""));
                    }
                    resultMap.put("rate", rowArray[2].replace(",", "."));
                    resultMap.put("date", discernmentDate(rowArray[3]));

                }
                Refbook refbook = refbookRepository.findOneByMcc(resultMap.get("mcc"));
                if (refbook != null){
                    resultMap.put("country", refbook.getCountry());
                }else {
                    resultMap.put("country", "Not found");
                }
                List<Refbook> refbookList = refbookRepository.findByMccAndMnc(resultMap.get("mcc"), resultMap.get("mnc"));
                if (refbookList.size() > 0){
                    resultMap.put("network", refbookList.get(0).getNetwork());
                }else {
                    resultMap.put("network", "Not found");
                }
                resultList.add(resultMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Map<String, String> getRnCountryNetworkByMccMnc(String mcc, String mnc) {
                Map<String, String> resultMap = new HashMap<>();
                Refbook refbook = refbookRepository.findOneByMcc(mcc);
                if (refbook != null){
                    resultMap.put("country", refbook.getCountry());
                }else {
                    resultMap.put("country", "Not found");
                }
                List<Refbook> refbookList = refbookRepository.findByMccAndMnc(mcc, mnc);
                if (refbookList.size() > 0){
                    resultMap.put("network", refbookList.get(0).getNetwork());
                }else {
                    resultMap.put("network", "Not found");
                }

        return resultMap;
    }

    private String discernmentDate(String dateStr){
        String resultDateString = "";
        for (DateFormat dateFormat: dateFormatService.getAllDateFormat()) {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(dateFormat.getFormat());
            try {
                Date date= format.parse(dateStr);
                if (date.getTime() < 946684800000l || date.getTime() > 4102444800000l) throw new Exception();
                resultDateString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
                break;
            } catch (Exception e) {
            }
        }
        return resultDateString;
    }

    @Override
    public Refbook getRefbookByNumber(String number) {
        Refbook returnValue = null;

        List<String> dialcodes = new ArrayList<>();
        Integer length = number.length() - 1;
        for (Integer i=0;i < length; i++) {
            dialcodes.add(number.substring(0, length - i));
        }

        Query query = entityManager.createQuery("FROM Refbook c WHERE c.dialCode IN (:dialcodes) AND c.state = 'dialcode' ORDER BY c.dialCode DESC");
        query.setParameter("dialcodes", dialcodes);
        List<Refbook> list = query.getResultList();
        if (list.size() > 0) {
            returnValue = list.get(0);
        }

        return returnValue;
    }

    @Override
    public List<Refbook> findAllRefbookByMcc(String mcc) {
        return refbookRepository.findByMcc(mcc);
    }

}
