package app.service.ratemodeReports;

import app.entity.*;
import app.entityXML.RateReports.ActualRatesReportRow;
import app.entityXML.RateReports.RatesHistoryReportRow;
import app.service.clientRatesCurrent.ClientRatesCurrentService;
import app.service.clientRatesHistroy.ClientRatesHistoryService;
import app.service.refbook.RefbookService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppVendorIps.SmppVendorIpsService;
import app.service.vendorRatesCurrent.VendorRatesCurrentService;
import app.service.vendorRatesHistory.VendorRatesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 02.12.2017.
 */

@Service
public class RatesReportsServiceImpl implements RatesReportsService {

    @Autowired
    ClientRatesCurrentService clientRatesCurrentService;

    @Autowired
    RefbookService refbookService;

    @Autowired
    SmppClientAccountService smppClientAccountService;

    @Autowired
    SmppVendorIpsService smppVendorIpsService;

    @Autowired
    ClientRatesHistoryService clientRatesHistoryService;

    @Autowired
    VendorRatesCurrentService vendorRatesCurrentService;

    @Autowired
    VendorRatesHistoryService vendorRatesHistoryService;

    @Override
    public List<ActualRatesReportRow> getActualRatesReport(String mode, Long account, String country) {
        List<ActualRatesReportRow> returnList = new ArrayList<>();
        if (mode.equals("client")) {
            List<ClientRatesCurrent> list = clientRatesCurrentService.getCurrentRatesByAccountAndCountry(account, country);
            for (ClientRatesCurrent rate : list) {

                String systemId = rate.getSmppClientAccount().getCustomer().getName() + " - " + rate.getSmppClientAccount().getName() + " (";
                Integer it = 0;
                for (SmppClientSystemId sysId : rate.getSmppClientAccount().getSmppClientSystemIdList()) {
                    systemId = systemId + sysId.getSystemId();
                    if (it != rate.getSmppClientAccount().getSmppClientSystemIdList().size() - 1) {
                        systemId = systemId + ",";
                    }
                    it++;
                }
                systemId = systemId + ")";

                String countryName = "";
                Refbook countryRefbook = refbookService.getRootRefbookByMcc(rate.getMcc());
                if (countryRefbook != null) {
                    countryName = countryRefbook.getCountry();
                }

                String networkName = "FLAT";
                if (rate.getMnc() != null) {
                    if (!rate.getMnc().equals("")) {
                        List<Refbook> networkRefbookList = refbookService.getRefbookByMccAndMnc(rate.getMcc(), rate.getMnc());
                        if (networkRefbookList.size() > 0) {
                            networkName = networkRefbookList.get(0).getNetwork();
                        }
                    }
                }
                String mcc = rate.getMcc();
                String mnc = rate.getMnc();
                float currentRate = rate.getRate();
                String  newRate = "";
                String managerName = "Not set";
                CustomUser manager = rate.getSmppClientAccount().getCustomer().getManager();
                if (manager != null) {
                    managerName = manager.getName();
                }

                ClientRatesHistory futureRate = clientRatesHistoryService.getNextRateAfterDate(rate.getSmppClientAccount(), rate.getMcc(), rate.getMnc(), new Date());
                if (futureRate != null) {
                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    newRate = futureRate.getRate().toString() + "   →   " +  df.format(futureRate.getDate());
                } else {
                    newRate = "No Changes";
                }


                returnList.add(new ActualRatesReportRow(systemId, countryName, networkName, mcc, mnc, currentRate, new Date(), newRate, managerName));
            }
        } else {
            List<VendorRatesCurrent> list = vendorRatesCurrentService.getCurrentRatesByAccountAndCountry(account, country);
            for (VendorRatesCurrent rate : list) {
                String systemId = rate.getSmppVendorIps().getSmppVendorAccount().getCustomer().getName() + " - " + rate.getSmppVendorIps().getSmppVendorAccount().getName() + " (" + rate.getSmppVendorIps().getSystemId() + ")";
                String countryName = "";
                Refbook countryRefbook = refbookService.getRootRefbookByMcc(rate.getMcc());
                if (countryRefbook != null) {
                    countryName = countryRefbook.getCountry();
                }

                String networkName = "FLAT";
                if (rate.getMnc() != null) {
                    if (!rate.getMnc().equals("")) {
                        List<Refbook> networkRefbookList = refbookService.getRefbookByMccAndMnc(rate.getMcc(), rate.getMnc());
                        if (networkRefbookList.size() > 0) {
                            networkName = networkRefbookList.get(0).getNetwork();
                        }
                    }
                }
                String mcc = rate.getMcc();
                String mnc = rate.getMnc();
                float currentRate = rate.getRate();
                String newRate = "";
                String managerName = "Not set";
                CustomUser manager = rate.getSmppVendorIps().getSmppVendorAccount().getCustomer().getManager();
                if (manager != null) {
                    managerName = manager.getName();
                }

                VendorRatesHistory futureRate = vendorRatesHistoryService.getNextRateAfterDate(rate.getSmppVendorIps(), rate.getMcc(), rate.getMnc(), new Date());
                if (futureRate != null) {
                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    newRate = futureRate.getRate().toString() + "   →   " +  df.format(futureRate.getDate());
                } else {
                    newRate = "No Changes";
                }


                returnList.add(new ActualRatesReportRow(systemId, countryName, networkName, mcc, mnc, currentRate, new Date(), newRate, managerName));
            }
        }

        return returnList;
    }


    @Override
    public List<RatesHistoryReportRow> getRatesHistoryReport(String mode, Long account, String country) {
        List<RatesHistoryReportRow> list = new ArrayList<>();

        if (mode.equals("client")) {
            SmppClientAccount acc = smppClientAccountService.getSmppClientAccountById(account);
            if (acc != null) {
                List<ClientRatesHistory> ratesHistory = clientRatesHistoryService.getHistoryByAccountAndMcc(acc, country);
                for (ClientRatesHistory rate : ratesHistory) {

                    String countryName = "";
                    Refbook countryRefbook = refbookService.getRootRefbookByMcc(rate.getMcc());
                    if (countryRefbook != null) {
                        countryName = countryRefbook.getCountry();
                    }

                    String networkName = "FLAT";
                    if (rate.getMnc() != null) {
                        if (!rate.getMnc().equals("")) {
                            List<Refbook> networkRefbookList = refbookService.getRefbookByMccAndMnc(rate.getMcc(), rate.getMnc());
                            if (networkRefbookList.size() > 0) {
                                networkName = networkRefbookList.get(0).getNetwork();
                            }
                        }
                    }
                    String mcc = rate.getMcc();
                    String mnc = rate.getMnc();

                    list.add(new RatesHistoryReportRow(countryName, networkName, mcc, mnc, rate.getDate(), rate.getRate(), rate.getClientRatesUpdate().getId()));
                }
            }
        }

        if (mode.equals("vendor")) {
            SmppVendorIps acc = smppVendorIpsService.getSmppVendorIpsById(account);
            if (acc != null) {
                List<VendorRatesHistory> ratesHistory = vendorRatesHistoryService.getHistoryByAccountAndMcc(acc, country);
                for (VendorRatesHistory rate : ratesHistory) {
                    String countryName = "";
                    Refbook countryRefbook = refbookService.getRootRefbookByMcc(rate.getMcc());
                    if (countryRefbook != null) {
                        countryName = countryRefbook.getCountry();
                    }

                    String networkName = "FLAT";
                    if (rate.getMnc() != null) {
                        if (!rate.getMnc().equals("")) {
                            List<Refbook> networkRefbookList = refbookService.getRefbookByMccAndMnc(rate.getMcc(), rate.getMnc());
                            if (networkRefbookList.size() > 0) {
                                networkName = networkRefbookList.get(0).getNetwork();
                            }
                        }
                    }
                    String mcc = rate.getMcc();
                    String mnc = rate.getMnc();

                    list.add(new RatesHistoryReportRow(countryName, networkName, mcc, mnc, rate.getDate(), rate.getRate(), rate.getVendorRatesUpdate().getId()));
                }
            }
        }



        return list;
    }
}
