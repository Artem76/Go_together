package app.service.tasks;

import app.entity.ClientRatesCurrent;
import app.entity.ClientRatesHistory;
import app.entity.VendorRatesCurrent;
import app.entity.VendorRatesHistory;
import app.service.logger.LoggerService;
import app.service.clientRatesCurrent.ClientRatesCurrentService;
import app.service.clientRatesHistroy.ClientRatesHistoryService;
import app.service.vendorRatesCurrent.VendorRatesCurrentService;
import app.service.vendorRatesHistory.VendorRatesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 29.11.2017.
 */

@Service
public class RateActualizator {

    @Autowired
    ClientRatesHistoryService clientRatesHistoryService;

    @Autowired
    VendorRatesHistoryService vendorRatesHistoryService;

    @Autowired
    ClientRatesCurrentService clientRatesCurrentService;

    @Autowired
    VendorRatesCurrentService vendorRatesCurrentService;

    @Autowired
    LoggerService loggerService;

    public void runParallelStream(){

        loggerService.writeInfo("Rate actualization task started");

        List<VendorRatesHistory> listVendorRates = vendorRatesHistoryService.getRatesToActulize();
        List<ClientRatesHistory> listClientRates = clientRatesHistoryService.getRatesToActulize();

        for (ClientRatesHistory currentRate : listClientRates) {

            if (clientRatesHistoryService.haveNewerUpdates(currentRate.getSmppClientAccount(), currentRate.getMcc(), currentRate.getMnc(), currentRate.getDate())) {
                currentRate.setActualized(true);
                clientRatesHistoryService.save(currentRate);
            } else {
                ClientRatesCurrent rate = clientRatesCurrentService.getRate(currentRate.getSmppClientAccount(), currentRate.getMcc(), currentRate.getMnc());
                if (rate == null) {
                    rate = new ClientRatesCurrent();
                }
                rate.setSmppClientAccount(currentRate.getSmppClientAccount());
                rate.setMcc(currentRate.getMcc());
                rate.setMnc(currentRate.getMnc());
                rate.setRate(currentRate.getRate());
                clientRatesCurrentService.save(rate);

                currentRate.setActualized(true);
                clientRatesHistoryService.save(currentRate);
            }
        }

        for (VendorRatesHistory currentRate : listVendorRates) {
            if (vendorRatesHistoryService.haveNewerUpdates(currentRate.getSmppVendorIps(), currentRate.getMcc(), currentRate.getMnc(), currentRate.getDate())) {
                currentRate.setActualized(true);
                vendorRatesHistoryService.save(currentRate);
            } else {
                VendorRatesCurrent rate = vendorRatesCurrentService.getRate(currentRate.getSmppVendorIps(), currentRate.getMcc(), currentRate.getMnc());
                if (rate == null) {
                    rate = new VendorRatesCurrent();
                }
                rate.setSmppVendorIps(currentRate.getSmppVendorIps());
                rate.setMcc(currentRate.getMcc());
                rate.setMnc(currentRate.getMnc());
                rate.setRate(currentRate.getRate());
                vendorRatesCurrentService.save(rate);

                currentRate.setActualized(true);
                vendorRatesHistoryService.save(currentRate);
            }
        }
        loggerService.writeInfo("Rate actualization task finished");
    }
}
