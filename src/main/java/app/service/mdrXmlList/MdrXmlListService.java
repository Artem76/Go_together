package app.service.mdrXmlList;

import app.entityXML.mdrList.MdrXmlList;
import app.entityXML.reportSmsStatistic.MessagingFlowRow;

import java.util.List;

/**
 * Created by АРТЕМ on 11.08.2017.
 */
public interface MdrXmlListService {
    MdrXmlList getMdrXmlListByParam(List<String> msgid_list_filter,
                                    List<String> vendor_msgid_list_filter,
                                    String created_at_start_filter,
                                    String created_at_end_filter,
                                    String smpp_client_account_id_filter,
                                    String smpp_vendor_account_id_filter,
                                    List<String> destination_addr_list_filter,
                                    List<String> source_addr_list_filter,
                                    String mcc_filter,
                                    List<String> mnc_list_filter,
                                    Long first_number,
                                    Long last_number,
                                    Integer amount_of_rows,
                                    String direction,
                                    String sort_list_in_string,
                                    Long min_id,
                                    Long max_id);

    List<MessagingFlowRow> getMessagingFlowList();
}
