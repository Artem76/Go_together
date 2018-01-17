package app.service.refbook;

import app.entity.Refbook;

import java.util.List;
import java.util.Map;

public interface RefbookService {
    List<Refbook> getRefbookAll();
    List<Refbook> getRefbookAllSortCountry();
    List<Refbook> getRefbookAllCountrySort();
    List<Refbook> getRefbookAllNetworkSort();
    List<Refbook> getRefbookAllSortMccMnc();
    List<Refbook> getRefbookNetworksByMcc(String mcc);
    List<Refbook> getRefbookDialcodesByMccAndMnc(String mcc, String mnc);
    Refbook getRefbookById(Long id);
    Refbook getRootRefbookByMcc(String mcc);
    List<Refbook> getRefbookByMccAndMnc(String mcc, String mnc);

    List<Map<String, String>> getCountryNetworkList();

    Boolean addRefbook(Refbook refbook);
    Boolean updateRefbook(Long id_refbook, String country, String mcc, String network, String mnc, String dialCode, Integer minLength, Integer maxLength);
    void deleteRefbook(Refbook refbook);
    Refbook getRefbookByNumber(String number);

    List<Map<String, String>> getRnHistoryList(Map<String, String> dataMap);

    Map<String, String> getRnCountryNetworkByMccMnc(String mcc, String mnc);
    List<Refbook> findAllRefbookByMcc(String mcc);
}
