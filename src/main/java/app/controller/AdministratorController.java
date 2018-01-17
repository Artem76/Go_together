package app.controller;

import app.entity.*;
import app.entity.enums.*;
import app.entityXML.BasicTests.BasicTestReportRow;
import app.entityXML.IndexReport.IndexManagerReport;
import app.entityXML.RateReports.ActualRatesReportRow;
import app.entityXML.RateReports.RatesHistoryReportRow;
import app.entityXML.Response.LongText;
import app.entityXML.Response.Response;
import app.entityXML.customerSmppVendorAccount.CustomerSmppVendorAccountList;
import app.entityXML.incomingTTXML.IncomingTTXML;
import app.entityXML.incomingTTXML.OutgoingTTXML;
import app.entityXML.mdr.TTMdr;
import app.entityXML.mdrList.MdrXmlList;
import app.entityXML.mncList.MncXmlList;
import app.entityXML.outgoingInvoiceTrafficDetails.OutgoingInvoiceRow;
import app.entityXML.reportSmsStatistic.MessagingFlowRow;
import app.entityXML.reportSmsStatistic.ReportSmsStatistic;
import app.entityXML.routingSmsRuleXmlList.RoutingSmsRuleXmlList;
import app.entityXML.soa.BalancesRow;
import app.entityXML.soa.SoaReport;
import app.repository.TestTextsRepository;
import app.service.basicTest.BasicTestService;
import app.service.basicTestDetails.BasicTestDetailService;
import app.service.ratemodeReports.RatesReportsService;
import app.service.pdf.PdfService;
import app.service.settings.SettingsService;
import app.service.clientRatesCurrent.ClientRatesCurrentService;
import app.service.incomingPayment.IncomingPaymentService;
import app.service.outgoingPayment.OutgoingPaymentService;
import app.service.soa.SoaService;
import app.service.bankAccount.BankAccountService;
import app.service.clientRatesHistroy.ClientRatesHistoryService;
import app.service.clientRatesUpdate.ClientRatesUpdateService;
import app.service.company.CompanyService;
import app.service.dateFormat.DateFormatService;
import app.service.emailAccount.EmailAccountService;
import app.service.emailAttachment.EmailAttachmentService;
import app.service.emailContent.EmailContentService;
import app.service.excel.ExcelService;
import app.service.incommingTT.IncomingTTService;
import app.service.indexReportService.IndexManagerReportService;
import app.service.mdr.MdrService;
import app.service.mdrXmlList.MdrXmlListService;
import app.service.messageLogForTT.MessageLogForTTService;
import app.service.mncList.MncListService;
import app.service.outgoingInvoice.OutgoingInvoiceService;
import app.service.outgoingInvoice.OutgoingInvoiceTrafficDetailsService;
import app.service.outgoingTT.OutgoingTTService;
import app.service.refbook.RefbookService;
import app.service.reportSmsStatistic.ReportSmsStatisticService;
import app.service.routingSmsRule.RoutingSmsRuleService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppClientIps.SmppClientIpsService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorAccountList.CustomerSmppVendorAccountListService;
import app.service.softswitch.SoftswitchService;
import app.service.coder.CoderService;
import app.service.customer.CustomerService;
import app.service.customerStatus.CustomerStatusService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.smppVendorIps.SmppVendorIpsService;
import app.service.smsBillingTerms.SmsBillingTermsService;
import app.service.softswitchTriggers.SoftswitchTriggersService;
import app.service.tasks.CachesUpdateTask;
import app.service.testNumbersPool.TestNumbersPoolService;
import app.service.user.UserService;
import app.service.vendorDialpeer.VendorDialpeerService;
import app.service.vendorDialpeerChild.VendorDialpeerChildService;
import app.service.vendorRatesCurrent.VendorRatesCurrentService;
import app.service.vendorRatesHistory.VendorRatesHistoryService;
import app.service.vendorRatesUpdate.VendorRatesUpdateService;
import app.service.voiceBillingTerms.VoiceBillingTermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


@Controller
public class AdministratorController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerStatusService customerStatusService;

    @Autowired
    private SmsBillingTermsService smsBillingTermsService;

    @Autowired
    private VoiceBillingTermsService voiceBillingTermsService;

    @Autowired
    private SmppVendorAccountService smppVendorAccountService;

    @Autowired
    private SmppVendorIpsService smppVendorIpsService;

    @Autowired
    private SmppClientAccountService smppClientAccountService;

    @Autowired
    private SmppClientIpsService smppClientIpsService;

    @Autowired
    private SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    private CoderService coderService;

    @Autowired
    private SoftswitchService softswitchService;

    @Autowired
    private ReportSmsStatisticService reportSmsStatisticService;

    @Autowired
    private RefbookService refbookService;

    @Autowired
    private MdrService mdrService;

    @Autowired
    private VendorDialpeerService vendorDialpeerService;

    @Autowired
    private VendorDialpeerChildService vendorDialpeerChildService;


    @Autowired
    private MncListService mncListService;

    @Autowired
    private MdrXmlListService mdrXmlListService;

    @Autowired
    private RoutingSmsRuleService routingSmsRuleService;

    @Autowired
    private CustomerSmppVendorAccountListService customerSmppVendorAccountListService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private IncomingTTService incomingTTService;

    @Autowired
    private OutgoingTTService outgoingTTService;

    @Autowired
    private SoftswitchTriggersService softswitchTriggersService;

    @Autowired
    private EmailAccountService emailAccountService;

    @Autowired
    private EmailContentService emailContentService;

    @Autowired
    private EmailAttachmentService emailAttachmentService;

    @Autowired
    private ClientRatesUpdateService clientRatesUpdateService;

    @Autowired
    private ClientRatesHistoryService clientRatesHistoryService;

    @Autowired
    private VendorRatesUpdateService vendorRatesUpdateService;

    @Autowired
    private VendorRatesHistoryService vendorRatesHistoryService;

    @Autowired
    private IndexManagerReportService indexManagerReportService;

    @Autowired
    private MessageLogForTTService messageLogForTTService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    TestTextsRepository testTextsRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private SoaService soaService;

    @Autowired
    private OutgoingInvoiceService outgoingInvoiceService;

    @Autowired
    private DateFormatService dateFormatService;

    @Autowired
    private OutgoingInvoiceTrafficDetailsService outgoingInvoiceTrafficDetailsService;

    @Autowired
    private CachesUpdateTask cachesUpdateTask;

    @Autowired
    private ClientRatesCurrentService clientRatesCurrentService;

    @Autowired
    private VendorRatesCurrentService vendorRatesCurrentService;

    @Autowired
    private RatesReportsService ratesReportsService;

    @Autowired
    private IncomingPaymentService incomingPaymentService;

    @Autowired
    private OutgoingPaymentService outgoingPaymentService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private TestNumbersPoolService testNumbersPoolService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    BasicTestService basicTestService;

    @Autowired
    BasicTestDetailService basicTestDetailService;


    @RequestMapping("/admin_full_index")
    public String admin(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("incoming_tt_count", 12);
        return "admin/admin_index";
    }


    //============================  User  =====================================


    @RequestMapping("/admin_full_user_list")
    public String adminUserList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        try {
            CustomUser customUser = userService.getUserByLogin(login);
            model.addAttribute("name", customUser.getName());
            model.addAttribute("role", customUser.getRole());
            model.addAttribute("users", userService.getUsersExceptRole(UserRole.Blocked));
            model.addAttribute("title", settingsService.getSiteTitle());
        } catch (Exception e) {
            return "redirect:/logout";
        }

        return "admin/admin_user_list";
    }

    @RequestMapping("/admin_full_user_list_blocked")
    public String adminUserListBlocked(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("users", userService.getUsersByRole(UserRole.Blocked));
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_user_list_blocked";
    }

    @RequestMapping("/admin_full_user_edit")
    public String adminUserEdit(@RequestParam long id_user,
                                @RequestParam String error,
                                @RequestParam String err_log,
                                @RequestParam String err_admin,
                                Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        if (id_user != 0) model.addAttribute("user", userService.getOne(id_user));
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("id_user", id_user);
        model.addAttribute("error", error);
        model.addAttribute("err_log", err_log);
        model.addAttribute("err_admin", err_admin);
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_user_edit";
    }

    @RequestMapping("/admin_full_user_edit_save")
    public String adminUserEditSave(@RequestParam long id_user,
                                    @RequestParam String name_user,
                                    @RequestParam String login_user,
                                    @RequestParam String password_user,
                                    @RequestParam String phone_user,
                                    @RequestParam String email_user,
                                    @RequestParam UserRole role_user) {
        if (role_user == null)
            return "redirect:/admin_full_user_edit?id_user=" + id_user + "&error=error&err_log=&err_admin=";

        try {
            if (id_user == 0) { //новый User
                if (password_user.equals(""))
                    return "redirect:/admin_full_user_edit?id_user=" + id_user + "&error=&err_log=error&err_admin=";
                CustomUser customUser = new CustomUser(name_user, login_user, coderService.coderSHA1(password_user), phone_user, email_user, role_user);
                if (!userService.addUser(customUser))
                    return "redirect:/admin_full_user_edit?id_user=" + id_user + "&error=&err_log=error";
            } else {           //Изменение User
                if (userService.getOne(id_user).getRole().equals(UserRole.Administrator) && userService.getUsersByRole(UserRole.Administrator).size() <= 1 && !role_user.equals(UserRole.Administrator))  //Проверка на последнего администратора
                    return "redirect:/admin_full_user_edit?id_user=" + id_user + "&error=&err_log=&err_admin=error";
                if (!userService.updateUser(id_user, name_user, login_user, password_user, password_user, phone_user, email_user, role_user))
                    return "redirect:/admin_full_user_edit?id_user=" + id_user + "&error=&err_log=error&err_admin=";
            }
        } catch (Exception e) {
            return "redirect:/admin_full_user_edit?id_user=" + id_user + "&error=error&err_log=&err_admin=";
        }
        return "redirect:/admin_full_user_list";
    }


    //============================  Monitoring Panel  =============================


    @RequestMapping("/admin_full_monitoring_panel")
    public String adminMonitoringPanel(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        Date now = new Date();
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = fd.format(now) + " 00:00:00";
        String dateEnd = fd.format(now) + " 23:59:59";
        model.addAttribute("date_start", dateStart);
        model.addAttribute("date_end", dateEnd);
        //ReportSmsStatistic reportSmsStatistic = reportSmsStatisticService.getReportSmsStatistic(customUser, 0L, "0", dateStart, dateEnd, "client");
        //model.addAttribute("reportSmsStatistic", reportSmsStatistic);
        Long attempts_count_sum = 0l;
        Long attempts_success_sum = 0l;
        Double incoming_sum_sum = 0.0;
        Double outgoing_sum_sum = 0.0;
        Double profit_sum = 0.0;
//        for (CustomerRssXml c : reportSmsStatistic.getCustomersRssXml()) {
//            attempts_count_sum += c.getAttempts_count();
//            attempts_success_sum += c.getAttempts_success();
//            incoming_sum_sum += c.getIncoming_sum();
//            outgoing_sum_sum += c.getOutgoing_sum();
//            profit_sum += c.getProfit();
//        }
        model.addAttribute("attempts_count_sum", Math.rint(100.0 * attempts_count_sum) / 100.0);
        model.addAttribute("attempts_success_sum", Math.rint(100.0 * attempts_success_sum) / 100.0);
        model.addAttribute("incoming_sum_sum", Math.rint(100.0 * incoming_sum_sum) / 100.0);
        model.addAttribute("outgoing_sum_sum", Math.rint(100.0 * outgoing_sum_sum) / 100.0);
        model.addAttribute("profit_sum", Math.rint(100.0 * profit_sum) / 100.0);
        model.addAttribute("customers", customerService.getCustomerAllSort());
        model.addAttribute("refbooks", refbookService.getRefbookAllCountrySort());
        return "admin/admin_monitoring_panel";
    }


    //============================  Refbook  =============================


    @RequestMapping("/admin_full_refbook_list")
    public String adminRefbookList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("refbooks", refbookService.getRefbookAllCountrySort());
        return "admin/admin_refbook_list";
    }

    @RequestMapping("/admin_full_refbook_network_list")
    public String adminRefbookNetworkList(@RequestParam String mcc, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("mcc", mcc);
        model.addAttribute("refbooks", refbookService.getRefbookNetworksByMcc(mcc));
        model.addAttribute("country_name", refbookService.getRootRefbookByMcc(mcc).getCountry());
        return "admin/admin_refbook_network_list";
    }

    @RequestMapping("/admin_full_refbook_dialcode_list")
    public String adminRefbookDialcodeList(@RequestParam String mcc, @RequestParam String mnc, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("mcc", mcc);
        model.addAttribute("mnc", mnc);
        model.addAttribute("refbooks", refbookService.getRefbookDialcodesByMccAndMnc(mcc, mnc));
        model.addAttribute("country_name", refbookService.getRootRefbookByMcc(mcc).getCountry() + " - " + refbookService.getRefbookByMccAndMnc(mcc, mnc).get(0).getNetwork());
        return "admin/admin_refbook_dialcode_list";
    }

    @RequestMapping("/admin_full_refbook_edit")
    public String adminRefbookEdit(@RequestParam Long id_refbook,
                                   @RequestParam String type,
                                   @RequestParam String error,
                                   @RequestParam(required = false) String mcc,
                                   @RequestParam(required = false) String mnc,
                                   Model model) {
        if (mcc == null) mcc = "";
        if (mnc == null) mnc = "";

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("id_refbook", id_refbook);
        model.addAttribute("mcc", mcc);
        model.addAttribute("mnc", mnc);
        if (id_refbook == 0) {
            if (type.equals("country")) {
                model.addAttribute("refbook_title", "* New country");
            }
            if (type.equals("network")) {
                model.addAttribute("refbook_title", "* New network");
                model.addAttribute("country_title", refbookService.getRootRefbookByMcc(mcc).getCountry());
            }
            if (type.equals("dialcode")) {
                model.addAttribute("refbook_title", "* New dialcode");
                model.addAttribute("country_title", refbookService.getRootRefbookByMcc(mcc).getCountry() + " - " + refbookService.getRefbookByMccAndMnc(mcc, mnc).get(0).getNetwork());
            }
        } else {
            if (type.equals("country")) {
                model.addAttribute("refbook_title", refbookService.getRefbookById(id_refbook).getCountry());
            }
            if (type.equals("network")) {
                model.addAttribute("refbook_title", refbookService.getRefbookById(id_refbook).getNetwork());
                model.addAttribute("country_title", refbookService.getRootRefbookByMcc(mcc).getCountry());
            }
            if (type.equals("dialcode")) {
                model.addAttribute("refbook_title", refbookService.getRefbookById(id_refbook).getDialCode());
                model.addAttribute("country_title", refbookService.getRootRefbookByMcc(mcc).getCountry() + " - " + refbookService.getRefbookByMccAndMnc(mcc, mnc).get(0).getNetwork());
            }
        }
        if (id_refbook != 0) model.addAttribute("refbook", refbookService.getRefbookById(id_refbook));
        model.addAttribute("error", error);
        if (type.equals("country")) {
            return "admin/admin_refbook_edit";
        }
        if (type.equals("network")) {
            return "admin/admin_refbook_network_edit";
        }
        if (type.equals("dialcode")) {
            return "admin/admin_refbook_dialcode_edit";
        }


        return "/pages-404";
    }

    @RequestMapping("/admin_full_refbook_edit_save")
    public String adminRefbookSave(@RequestParam Long id_refbook,
                                   @RequestParam(required = false) String country_refbook,
                                   @RequestParam(required = false) String mcc_refbook,
                                   @RequestParam(required = false) String network_refbook,
                                   @RequestParam(required = false) String mnc_refbook,
                                   @RequestParam(required = false) String dialCode_refbook,
                                   @RequestParam String minLength_refbook,
                                   @RequestParam String maxLength_refbook,
                                   @RequestParam String state) {
        try {
            if (country_refbook == null) country_refbook = "";
            if (mcc_refbook == null) mcc_refbook = "";
            if (network_refbook == null) network_refbook = "";
            if (mnc_refbook == null) mnc_refbook = "";
            if (minLength_refbook.equals("")) minLength_refbook = "0";
            if (maxLength_refbook.equals("")) maxLength_refbook = "0";
            int minLength = Integer.parseInt(minLength_refbook);
            int maxLength = Integer.parseInt(maxLength_refbook);
            if (id_refbook == 0) { //новый Refbook
                Refbook refbook = new Refbook(mcc_refbook, mnc_refbook, dialCode_refbook, country_refbook, network_refbook, minLength, maxLength, state);
                if (!refbookService.addRefbook(refbook))
                    return "redirect:/admin_full_refbook_edit?id_refbook=" + id_refbook + "&error=error";
            } else {           //Изменение Refbook
                if (!refbookService.updateRefbook(id_refbook, country_refbook, mcc_refbook, network_refbook, mnc_refbook, dialCode_refbook, minLength, maxLength))
                    return "redirect:/admin_full_refbook_edit?id_refbook=" + id_refbook + "&error=error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (state.equals("country")) {
            return "redirect:/admin_full_refbook_list";
        }
        if (state.equals("network")) {
            return "redirect:/admin_full_refbook_network_list?mcc=" + mcc_refbook;
        }
        if (state.equals("dialcode")) {
            return "redirect:/admin_full_refbook_dialcode_list?mcc=" + mcc_refbook + "&mnc=" + mnc_refbook;
        }

        return "redirect:/admin_full_refbook_list";
    }

    @RequestMapping("/admin_full_refbook_delete")
    public String adminRefbookDelete(@RequestParam(value = "id_refbook") Refbook refbook,
                                     @RequestParam(required = false) String mcc_refbook,
                                     @RequestParam(required = false) String mnc_refbook,
                                     @RequestParam(required = false) String state) {
        String currentMnc = refbook.getMnc();
        refbookService.deleteRefbook(refbook);
        if (state.equals("country")) {
            List<Refbook> currentNetworkList = refbookService.getRefbookNetworksByMcc(mcc_refbook);
            List<Refbook> currentDialcodeList = refbookService.getRefbookDialcodesByMccAndMnc(mcc_refbook, mnc_refbook);
            for (Refbook currentRefbook : currentDialcodeList) {
                refbookService.deleteRefbook(currentRefbook);
            }
            for (Refbook currentRefbook : currentNetworkList) {
                refbookService.deleteRefbook(currentRefbook);
            }
            return "redirect:/admin_full_refbook_list";
        }
        if (state.equals("network")) {
            List<Refbook> currentDialcodeList = refbookService.getRefbookDialcodesByMccAndMnc(mcc_refbook, currentMnc);
            for (Refbook currentRefbook : currentDialcodeList) {
                refbookService.deleteRefbook(currentRefbook);
            }
            return "redirect:/admin_full_refbook_network_list?mcc=" + mcc_refbook;
        }
        if (state.equals("dialcode")) {
            return "redirect:/admin_full_refbook_dialcode_list?mcc=" + mcc_refbook + "&mnc=" + mnc_refbook;
        }
        return "redirect:/admin_full_refbook_list";
    }

    //============================  Routing  ======================================

    @RequestMapping("/admin_full_routing")
    public String adminRouting(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("smppClientAccountList", smppClientAccountService.getSmppClientAccountAll());
        model.addAttribute("countriesList", refbookService.getRefbookAllCountrySort());
        model.addAttribute("smsRoutingLevelList", SmsRoutingLevel.values());
        return "admin/admin_routing";
    }


    //============================  Client RN ============================================


    @RequestMapping("/admin_full_client_rn_list")
    public String adminClientRNList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
//        Date now = new Date();
//        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
//        String dateStart = fd.format(now) + " 00:00:00";
//        String dateEnd = fd.format(now) + " 23:59:59";
        List<SmppClientAccount> list = smppClientAccountService.getSmppClientAccountAll();
        model.addAttribute("accounts", list);
//        model.addAttribute("date_start", dateStart);
//        model.addAttribute("date_end", dateEnd);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("refbooks", refbookService.getRefbookAllCountrySort());
        return "admin/admin_client_rn_list";
    }


    @RequestMapping("/admin_full_client_rn_edit")
    public String adminClientRNEdit(@RequestParam long id,
                                    Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("id_rn", id);
        ClientRatesUpdate clientRatesUpdate = null;
        if (id == 0l) {
            clientRatesUpdate = new ClientRatesUpdate();
            EmailAccount emailAccount = emailAccountService.getAllEmailAccountByEmailRole(EmailRole.Ratemode_SMS).get(0);
            clientRatesUpdate.setTextEmail(emailAccount.getTextEmailClientRn() + emailAccount.getSignature());
        } else {
            clientRatesUpdate = clientRatesUpdateService.getRnById(id);
        }
        model.addAttribute("rn", clientRatesUpdate);
        model.addAttribute("accounts", smppClientAccountService.getSmppClientAccountAll());
        model.addAttribute("countries", refbookService.getRefbookAllCountrySort());
        model.addAttribute("rates", clientRatesHistoryService.getRateListMapByUpdate(clientRatesUpdateService.getRnById(id)));
        return "admin/admin_client_rn_edit";
    }

    @RequestMapping(value = "/admin_full_save_client_rn_update", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, Object> saveClientRNUpdates(@RequestParam Long id_rn,
                                            @RequestParam Long account_id,
                                            @RequestParam String textEmail,
                                            @RequestParam Map<String, String> rates,
                                            @RequestParam Integer arrLastNumber) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Map result = new HashMap();
        List<String> errors = new ArrayList<>();
        SmppClientAccount smppClientAccount = null;

        //=====Проверка данных========
        try {
            smppClientAccount = smppClientAccountService.getSmppClientAccountById(account_id);
            if (smppClientAccount == null) throw new Exception();
        } catch (Exception e) {
            errors.add("account_id");
        }

        try {
            Set<String> contentRnHistorySet = new HashSet<>();
            for (int i = 0; i <= arrLastNumber; i++) {
                String mcc = rates.get("rates[" + i + "mcc]");
                String mnc = null;
                if (rates.get("rates[" + i + "mnc]").equals("0") || rates.get("rates[" + i + "mnc]").equals("00") || rates.get("rates[" + i + "mnc]").equals("000")) {
                    mnc = "0";
                } else {
                    mnc = rates.get("rates[" + i + "mnc]").replaceFirst("^0*", "");
                }
                String dateString = rates.get("rates[" + i + "date]");
                if (!Pattern.matches("^[1-9][0-9]{2}$", mcc)) throw new Exception();
                if (!Pattern.matches("^[1-9][0-9]{0,2}$|^$|^0$", mnc)) throw new Exception();
                Float.parseFloat(rates.get("rates[" + i + "rate]"));
                if (!Pattern.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(20|21)\\d\\d\\ ([0-1]\\d|2[0-3])(:[0-5]\\d){2}$", dateString))
                    throw new Exception();
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd-MM-yyyy HH:mm:ss");
                Date date = format.parse(dateString);
                if (clientRatesHistoryService.hasClientRatesHistoryByDateSmppClientAccountMccMncExceptUpdate(date, smppClientAccount, mcc, mnc, clientRatesUpdateService.getRnById(id_rn)))
                    throw new Exception();
                String rnHistoryString = mcc + mnc + dateString;
                if (contentRnHistorySet.contains(rnHistoryString)) throw new Exception();
                contentRnHistorySet.add(rnHistoryString);
            }
        } catch (Exception e) {
            errors.add("rates");
        }

        //=====Сохранение данных======

        if (errors.size() == 0) {
            try {
                ClientRatesUpdate clientRatesUpdate;

                //новый ClientRatesUpdate
                if (id_rn == 0L)
                    clientRatesUpdate = clientRatesUpdateService.saveRateUpdate(new ClientRatesUpdate(customUser, new Date(), textEmail, smppClientAccount));
                else {           //Изменение ClientRatesUpdate
                    clientRatesUpdate = clientRatesUpdateService.getRnById(id_rn);
                    if (clientRatesUpdate.getEmailContentList().size() != 0) throw new Exception();
                    clientRatesUpdate.setUser(customUser);
                    clientRatesUpdate.setSmppClientAccount(smppClientAccount);
                    clientRatesUpdate.setTextEmail(textEmail);
                    clientRatesUpdate.setDate(new Date());
                    clientRatesUpdate = clientRatesUpdateService.saveRateUpdate(clientRatesUpdate);
                }

                if (clientRatesUpdate == null) throw new Exception();

                //=====Удаление ClientRateHistory======

                for (ClientRatesHistory clientRatesHistory : new ArrayList<>(clientRatesUpdate.getClientRatesHistories())) {
                    clientRatesHistoryService.deleteClientRatesHistory(clientRatesHistory);
                }

                clientRatesUpdate = clientRatesUpdateService.getRnById(clientRatesUpdate.getId());

                //======Создание новых ClientRatesHistory============

                Set<ClientRatesHistory> clientRatesHistoryList = new HashSet<>();
                for (int i = 0; i <= arrLastNumber; i++) {
                    String mcc = rates.get("rates[" + i + "mcc]");
                    String mnc = null;
                    if (rates.get("rates[" + i + "mnc]").equals("0") || rates.get("rates[" + i + "mnc]").equals("00") || rates.get("rates[" + i + "mnc]").equals("000")) {
                        mnc = "0";
                    } else {
                        mnc = rates.get("rates[" + i + "mnc]").replaceFirst("^0*", "");
                    }
                    Float rate = Float.parseFloat(rates.get("rates[" + i + "rate]"));
                    String dateString = rates.get("rates[" + i + "date]");
                    SimpleDateFormat format = new SimpleDateFormat();
                    format.applyPattern("dd-MM-yyyy HH:mm:ss");
                    Date date = format.parse(dateString);
                    ClientRatesHistory clientRatesHistory = clientRatesHistoryService.save(new ClientRatesHistory(date, smppClientAccount, mcc, mnc, rate, clientRatesUpdate));
                    clientRatesHistoryList.add(clientRatesHistory);
                }
                clientRatesUpdate.setClientRatesHistories(clientRatesHistoryList);
                clientRatesUpdateService.saveRateUpdate(clientRatesUpdate);
                result.put("id", clientRatesUpdate.getId());
            } catch (Exception e) {
                errors.add("server_error");
                e.printStackTrace();
            }
        }

        result.put("errors", errors);
        return result;
    }

    @RequestMapping(value = "/admin_full_client_rn_send_email", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, String> ClientRnSendEmail(@RequestParam Long id_rn) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        Map<String, String> mapError = new HashMap<>();
        SmppClientAccount smppClientAccount = clientRatesUpdateService.getRnById(id_rn).getSmppClientAccount();
        String emailString = smppClientAccount.getCustomer().getSmsRateModeEmail();
        if (emailString == null || emailString.equals("")) {
            mapError.put("error", "No email address!");
        } else if (!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", emailString.replace(" ", "").replaceFirst(",.*", ""))) {
            mapError.put("error", "Error email address!");
        } else if (smppClientAccount.getSmppClientSystemIdList().size() == 0) {
            mapError.put("error", "No system id!");
        } else {
            emailContentService.clientRnEmailContent(id_rn, customUser);
        }
        return mapError;
    }

    @RequestMapping(value = "/admin_full_get_rn_history_list_full", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<Map<String, String>> getClientRnHistoryListFull(@RequestParam Map<String, String> dataMap) {
        return refbookService.getRnHistoryList(dataMap);
    }

    @RequestMapping(value = "/admin_full_get_country_network_by_mcc_mnc", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, String> getCountryNetworkByMccMnc(@RequestParam String mcc,
                                                  @RequestParam String mnc) {
        return refbookService.getRnCountryNetworkByMccMnc(mcc, mnc);
    }

    @RequestMapping(value = "/admin_full_client_rn_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, Object> getClientRnList(@RequestParam String mcc,
                                        @RequestParam String mnc,
                                        @RequestParam Long account_id,
                                        @RequestParam Integer page_index,
                                        @RequestParam Integer page_size) {
        if (mcc.equals("") && mnc.equals("") && account_id == 0l) {
            //======Все clientRatesUpdate====
            return clientRatesUpdateService.getClientRatesUpdateSortForPage(page_index, page_size);
        } else if (mcc.equals("") && mnc.equals("") && account_id > 0l) {
            //======Только по account_id====
            return clientRatesUpdateService.getClientRatesUpdateBySmppClientAccountSortForPage(account_id, page_index, page_size);
        } else {
            //======При наличии mcc или mnc====
            return clientRatesUpdateService.getRateUpdateMapBySmppClientAccountAndMccAndMncForPage(account_id, mcc, mnc, page_index, page_size);
        }
    }

    @RequestMapping(value = "/admin_full_set_excel_and_get_string", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    HashMap<String, String> setExcelAndGetString(@RequestParam MultipartFile file) {
        HashMap<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                map.putAll(excelService.setExcelGetStringForPrice(file));
            } catch (IOException e) {
                e.printStackTrace();
                map.put("error", "error");
            }
        }
        return map;
    }

    @RequestMapping(value = "/admin_full_set_attachment_excel_and_get_string", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    HashMap<String, String> setAttachmentExcelAndGetString(@RequestParam long id_email_attachment) {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.putAll(excelService.setAttachmentExcelGetStringForPrice(emailAttachmentService.getEmailAttachmentById(id_email_attachment)));
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", "error");
        }
        return map;
    }


    //============================  Vendor RN ============================================


    @RequestMapping("/admin_full_vendor_rn_list")
    public String adminVendorRNList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("accountIps", smppVendorIpsService.getSmppVendorIpsAllSort());
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("refbooks", refbookService.getRefbookAllCountrySort());
        return "admin/admin_vendor_rn_list";
    }


    @RequestMapping("/admin_full_vendor_rn_edit")
    public String adminVendorRNEdit(@RequestParam long id_vendor_rn,
                                    @RequestParam long id_email_content,
                                    @RequestParam long id_email_attachment,
                                    Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("id_rn", id_vendor_rn);
        model.addAttribute("id_email_content", id_email_content);
        model.addAttribute("id_email_attachment", id_email_attachment);
        model.addAttribute("rn", vendorRatesUpdateService.getRnById(id_vendor_rn));
        model.addAttribute("accountIps", smppVendorIpsService.getSmppVendorIpsAllSort());
        model.addAttribute("rates", vendorRatesHistoryService.getRateListMapByUpdate(vendorRatesUpdateService.getRnById(id_vendor_rn)));
        return "admin/admin_vendor_rn_edit";
    }

    @RequestMapping(value = "/admin_full_save_vendor_rn_update", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> saveVendorRNUpdates(@RequestParam Long id_rn,
                                     @RequestParam Long accountIps_id,
                                     @RequestParam Long id_email_content,
                                     @RequestParam Map<String, String> rates,
                                     @RequestParam Integer arrLastNumber) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        List<String> errors = new ArrayList<>();
        SmppVendorIps smppVendorIps = null;

        //=====Проверка данных========
        try {
            smppVendorIps = smppVendorIpsService.getSmppVendorIpsById(accountIps_id);
            if (smppVendorIps == null) throw new Exception();
        } catch (Exception e) {
            errors.add("accountIps_id");
        }

        try {
            Set<String> contentRnHistorySet = new HashSet<>();
            for (int i = 0; i <= arrLastNumber; i++) {
                String mcc = rates.get("rates[" + i + "mcc]");
                String mnc = null;
                if (rates.get("rates[" + i + "mnc]").equals("0") || rates.get("rates[" + i + "mnc]").equals("00") || rates.get("rates[" + i + "mnc]").equals("000")) {
                    mnc = "0";
                } else {
                    mnc = rates.get("rates[" + i + "mnc]").replaceFirst("^0*", "");
                }
                String dateString = rates.get("rates[" + i + "date]");
                if (!Pattern.matches("^[1-9][0-9]{2}$", mcc)) throw new Exception();
                if (!Pattern.matches("^[1-9][0-9]{0,2}$|^$|^0$", mnc)) throw new Exception();
                Float.parseFloat(rates.get("rates[" + i + "rate]"));
                if (!Pattern.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(20|21)\\d\\d\\ ([0-1]\\d|2[0-3])(:[0-5]\\d){2}$", dateString))
                    throw new Exception();
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd-MM-yyyy HH:mm:ss");
                Date date = format.parse(dateString);
                if (vendorRatesHistoryService.hasVendorRatesHistoryByDateSmppVendorIpsMccMncExceptUpdate(date, smppVendorIps, mcc, mnc, vendorRatesUpdateService.getRnById(id_rn)))
                    throw new Exception();
                String rnHistoryString = mcc + mnc + dateString;
                if (contentRnHistorySet.contains(rnHistoryString)) {
                    System.out.println(rnHistoryString);
                    throw new Exception();
                }
                contentRnHistorySet.add(rnHistoryString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("rates");
        }

        //=====Сохранение данных======

        if (errors.size() == 0) {
            try {
                VendorRatesUpdate vendorRatesUpdate;
                EmailContent emailContent = emailContentService.getEmailContentById(id_email_content);

                if (id_rn == 0L) { //новый VendorRatesUpdate
                    vendorRatesUpdate = vendorRatesUpdateService.saveRateUpdate(new VendorRatesUpdate(customUser, new Date(), smppVendorIps, emailContent));
                } else {           //Изменение VendorRatesUpdate
                    vendorRatesUpdate = vendorRatesUpdateService.getRnById(id_rn);
                    vendorRatesUpdate.setUser(customUser);
                    vendorRatesUpdate.setSmppVendorIps(smppVendorIps);
//                    vendorRatesUpdate.setEmailContent(emailContent);
                    vendorRatesUpdate = vendorRatesUpdateService.saveRateUpdate(vendorRatesUpdate);
                }
                emailContentService.updateVendorRatesUpdate(emailContent, vendorRatesUpdate);

                if (vendorRatesUpdate == null) throw new Exception();

                //=====Удаление VendorRateHistory======

                for (VendorRatesHistory vendorRatesHistory : new ArrayList<>(vendorRatesUpdate.getVendorRatesHistories())) {
                    vendorRatesHistoryService.deleteVendorRatesHistory(vendorRatesHistory);
                }

                vendorRatesUpdate = vendorRatesUpdateService.getRnById(vendorRatesUpdate.getId());

                //======Создание новых VendorRatesHistory============

                List<VendorRatesHistory> vendorRatesHistoryList = new ArrayList<>();
                for (int i = 0; i <= arrLastNumber; i++) {
                    String mcc = rates.get("rates[" + i + "mcc]");
                    String mnc = null;
                    if (rates.get("rates[" + i + "mnc]").equals("0") || rates.get("rates[" + i + "mnc]").equals("00") || rates.get("rates[" + i + "mnc]").equals("000")) {
                        mnc = "0";
                    } else {
                        mnc = rates.get("rates[" + i + "mnc]").replaceFirst("^0*", "");
                    }
                    Float rate = Float.parseFloat(rates.get("rates[" + i + "rate]"));
                    String dateString = rates.get("rates[" + i + "date]");
                    SimpleDateFormat format = new SimpleDateFormat();
                    format.applyPattern("dd-MM-yyyy HH:mm:ss");
                    Date date = format.parse(dateString);
                    VendorRatesHistory vendorRatesHistory = vendorRatesHistoryService.save(new VendorRatesHistory(date, smppVendorIps, mcc, mnc, rate, vendorRatesUpdate));
                    vendorRatesHistoryList.add(vendorRatesHistory);
                }
                vendorRatesUpdate.setVendorRatesHistories(vendorRatesHistoryList);
                vendorRatesUpdateService.saveRateUpdate(vendorRatesUpdate);

            } catch (Exception e) {
                errors.add("server_error");
                e.printStackTrace();
            }
        }

        return errors;
    }

    @RequestMapping(value = "/admin_full_vendor_rn_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, Object> getVendorRnList(@RequestParam String mcc,
                                        @RequestParam String mnc,
                                        @RequestParam Long accountIp_id,
                                        @RequestParam Integer page_index,
                                        @RequestParam Integer page_size) {
        if (mcc.equals("") && mnc.equals("") && accountIp_id == 0l) {
            //======Все vendorRatesUpdate====
            return vendorRatesUpdateService.getVendorRatesUpdateSortForPage(page_index, page_size);
        } else if (mcc.equals("") && mnc.equals("") && accountIp_id > 0l) {
            //======Только по accountIp_id====
            return vendorRatesUpdateService.getVendorRatesUpdateBySmppVendorIpsSortForPage(accountIp_id, page_index, page_size);
        } else {
            //======При наличии mcc или mnc====
            return vendorRatesUpdateService.getRateUpdateMapBySmppVendorIpsAndMccAndMncForPage(accountIp_id, mcc, mnc, page_index, page_size);
        }
    }


    //============================ incoming payments ============================================

    @RequestMapping("/admin_full_sms_incoming_payment_list")
    public String adminIncomingPaymentList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        Date now = new Date();
        SimpleDateFormat fdStart = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat fdEnd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = fdStart.format(now) + "-01 00:00:00";
        String dateEnd = fdEnd.format(now) + " 23:59:59";
        model.addAttribute("customers", customerService.getCustomerAllSort());
        model.addAttribute("date_start", dateStart);
        model.addAttribute("date_end", dateEnd);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_sms_incoming_payment_list";
    }

    @RequestMapping(value = "/admin_full_sms_incoming_payment_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, Object> getSmsIncopmingPaymentList(@RequestParam Long customer_id,
                                                   @RequestParam String date_start,
                                                   @RequestParam String date_end,
                                                   @RequestParam Integer page_index,
                                                   @RequestParam Integer page_size) {

        return incomingPaymentService.getPaymentMapSortForPage(customer_id, date_start, date_end, page_index, page_size);
    }

    @RequestMapping(value = "/admin_full_sms_incoming_payment_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> saveIncomingPayment(@RequestParam String confirmed,
                                     @RequestParam Long customer_id,
                                     @RequestParam Long payment_id,
                                     @RequestParam String date,
                                     @RequestParam Double sum) {
        List<String> errorList = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = format.parse(date);
            Boolean confirmedBoolean = Boolean.parseBoolean(confirmed);
            if (customer_id == 0) {
                errorList.add("customer_id");
                throw new Exception();
            }
            if (sum == null || sum == 0) {
                sum = 0.0;
            }
            incomingPaymentService.savePayment(confirmedBoolean, customer_id, payment_id, d, sum);
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("server error");
        }
        return errorList;
    }


    //============================ outgoing payments ============================================

    @RequestMapping("/admin_full_sms_outgoing_payment_list")
    public String adminOutgoingPaymentList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        Date now = new Date();
        SimpleDateFormat fdStart = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat fdEnd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = fdStart.format(now) + "-01 00:00:00";
        String dateEnd = fdEnd.format(now) + " 23:59:59";
        model.addAttribute("customers", customerService.getCustomerAllSort());
        model.addAttribute("date_start", dateStart);
        model.addAttribute("date_end", dateEnd);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_sms_outgoing_payment_list";
    }

    @RequestMapping(value = "/admin_full_sms_outgoing_payment_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, Object> getSmsOutgoingPaymentList(@RequestParam Long customer_id,
                                                  @RequestParam String date_start,
                                                  @RequestParam String date_end,
                                                  @RequestParam Integer page_index,
                                                  @RequestParam Integer page_size) {

        return outgoingPaymentService.getPaymentMapSortForPage(customer_id, date_start, date_end, page_index, page_size);
    }

    @RequestMapping(value = "/admin_full_sms_outgoing_payment_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> saveOutgoingPayment(@RequestParam Long customer_id,
                                     @RequestParam Long payment_id,
                                     @RequestParam String date,
                                     @RequestParam Double sum) {
        List<String> errorList = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = format.parse(date);
            if (customer_id == 0) {
                errorList.add("customer_id");
                throw new Exception();
            }
            if (sum == null || sum == 0) {
                sum = 0.0;
            }
            outgoingPaymentService.savePayment(customer_id, payment_id, d, sum);
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("server error");
        }
        return errorList;
    }


    @RequestMapping("/admin_full_basic_test")
    public String adminBasicTestsList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("countries", refbookService.getRefbookAllCountrySort());
        model.addAttribute("accounts", customerSmppVendorAccountListService.getFullAccountList().getCustomerSmppVendorAccountList());
        model.addAttribute("templates", testTextsRepository.findAll());
        return "admin/admin_basic_test_list";
    }


    //============================ test numbers pool ============================================

    @RequestMapping("/admin_full_test_numbers_pool")
    public String adminTestNumbersPool(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("countryNetworkList", refbookService.getCountryNetworkList());
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_test_numbers_pool";
    }

    @RequestMapping(value = "/admin_full_test_numbers_pool", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<Map<String, String>> getTestNumbersPool(@RequestParam String mcc_mnc) {
        return testNumbersPoolService.getTestNumberMapSort(mcc_mnc);
    }

    @RequestMapping(value = "/admin_full_get_mcc_mnc_by_number", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, String> getMccMncByNumber(@RequestParam String number) {
        Refbook refbook = refbookService.getRefbookByNumber(number);
        Map<String, String> map = new HashMap<>();
        if (refbook != null) {
            map.put("mcc", refbook.getMcc());
            map.put("mnc", refbook.getMnc());
        } else {
            map.put("mcc", "");
            map.put("mnc", "");
        }
        return map;
    }

    @RequestMapping(value = "/admin_full_test_numbers_pool_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> saveTestNumbersPool(@RequestParam String invalid,
                                     @RequestParam Long id,
                                     @RequestParam String mcc,
                                     @RequestParam String mnc,
                                     @RequestParam String number) {
        List<String> errorList = new ArrayList<>();
        Boolean invalidBoolean = null;
        try {
            invalidBoolean = Boolean.parseBoolean(invalid);
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("invalid");
        }
        if (mcc == null || mcc.equals("")) errorList.add("mcc");
        if (mnc == null || mnc.equals("")) errorList.add("mnc");
        if (number == null || number.equals("") || testNumbersPoolService.hasNumber(number, id))
            errorList.add("number");

        if (errorList.size() == 0) {
            try {
                testNumbersPoolService.save(id, mcc, mnc, number, invalidBoolean);
            } catch (Exception e) {
                errorList.add("server error");
                e.printStackTrace();
            }
        }
        return errorList;
    }

    @RequestMapping(value = "/admin_full_test_numbers_pool_delete", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> deleteTestNumbersPool(@RequestParam Long id) {
        List<String> errorList = new ArrayList<>();
        try {
            testNumbersPoolService.delete(id);
        } catch (Exception e) {
            errorList.add("server error");
            e.printStackTrace();
        }
        return errorList;
    }


    //============================ incoming TT ============================================

    @RequestMapping("/admin_full_incoming_tt_list")
    public String adminIncomingTTList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_incoming_tt_list";
    }

    @RequestMapping("/admin_full_incoming_tt_history_list")
    public String adminIncomingTTHistoryList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        Date now = new Date();
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = fd.format(now) + " 00:00:00";
        String dateEnd = fd.format(now) + " 23:59:59";
        model.addAttribute("date_start", dateStart);
        model.addAttribute("date_end", dateEnd);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customers", customerService.getCustomerAll());

        return "admin/admin_incoming_tt_history_list";
    }

    @RequestMapping(value = "/admin_full_get_incoming_tt_list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<IncomingTTXML> getIncominTTList() {
        return incomingTTService.getOpenedTTXMLList();
    }

    @RequestMapping(value = "/admin_full_get_incoming_tt_history_list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<IncomingTTXML> getIncominTTHistoryList(@RequestParam Long customer_id,
                                                @RequestParam String startDate,
                                                @RequestParam String endDate) {
        Customer customer = null;
        if (customer_id != 0) {
            customer = customerService.getCustomerById(customer_id);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date filterStartDate = null;
        Date filterEndDate = null;
        try {
            filterStartDate = formatter.parse(startDate);
            filterEndDate = formatter.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return incomingTTService.getOpenedTTHistoryXMLList(filterStartDate, filterEndDate, customer);
    }

    @RequestMapping("/admin_full_incoming_tt_edit")
    public String adminIncomingTTEdit(@RequestParam long id_incoming_tt,
                                      Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        IncomingTT tt = null;
        SmppClientAccount currentAccount = null;
        if (id_incoming_tt == 0) {
            tt = new IncomingTT();
            model.addAttribute("id_tt", 0);
        } else {
            model.addAttribute("id_tt", id_incoming_tt);
            tt = incomingTTService.getTtById(id_incoming_tt);
            currentAccount = smppClientAccountService.getSmppClientAccountById(tt.getCustomer_account_id());

        }

        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customer", currentAccount);
        model.addAttribute("statuses", TTStatuses.values());
        model.addAttribute("tt", tt);
        List<EmailContent> emailContentList = tt.getEmailContentList();
        emailContentList.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        model.addAttribute("emailContentList", emailContentList);
        List<Map<String, String>> outgoingTTList = new ArrayList<>();
        for (OutgoingTT o : tt.getOutgoingTTList()) {
            Map<String, String> oMap = new HashMap<>();
            oMap.put("id", String.valueOf(o.getId()));
            oMap.put("subject", o.getSubject());
            SmppVendorAccount smppVendorAccount = smppVendorAccountService.getSmppVendorAccountById(o.getCustomer_account_id());
            oMap.put("customer_account", smppVendorAccount.getCustomer().getName() + " - " + smppVendorAccount.getName());
            oMap.put("date_opened", o.getDate().toGMTString());
            oMap.put("date_closed", (o.getDateClosed() == null) ? "" : o.getDateClosed().toGMTString());
            oMap.put("status", o.getStatus().name());
            outgoingTTList.add(oMap);
        }
        model.addAttribute("outgoingTTList", outgoingTTList);
        model.addAttribute("customers", smppClientAccountService.getSmppClientAccountAll());
        model.addAttribute("issueList", MessageIssueType.values());
        return "admin/admin_incoming_tt_edit";
    }

    @RequestMapping(value = "/admin_full_get_messages_by_incoming_tt_id", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<TTMdr> getMessagesByTTId(@RequestParam Long tt_id) {
        List<TTMdr> TTMdrList = mdrService.getIncomingTTMdrByTTId(tt_id);
        return TTMdrList;
    }

    @RequestMapping(value = "/admin_full_get_message_by_id", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    TTMdr getMessageByMsgid(@RequestParam String msgid) {
        TTMdr currentMessage = mdrService.getTTMdrByMsgid(msgid);
        return currentMessage;
    }


    @RequestMapping(value = "/admin_full_save_incoming_tt", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> saveIncomingTT(@RequestParam Long id_tt,
                                @RequestParam TTStatuses status,
                                @RequestParam Long customer_account,
                                @RequestParam String subject,
                                @RequestParam String solution,
                                @RequestParam Map<String, String> messageLogForTTList,
                                @RequestParam Integer arrLastNumber) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        List<String> errors = new ArrayList<>();

        //=====Проверка данных========
        if (customer_account == null || customer_account == 0L) errors.add("customer_account");

        if (subject == null || subject.equals("")) errors.add("subject");

        Set<Long> messageLogForTT_id_set = new HashSet<>();
        Set<String> msgid_set = new HashSet<>();
        try {
            for (int i = 0; i <= arrLastNumber; i++) {
                Long.parseLong(messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]"));
                if (messageLogForTTList.get("messageLogForTTList[" + i + "msgid]").equals("") || msgid_set.contains(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]")))
                    throw new Exception();
                msgid_set.add(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]"));
                if (MessageIssueType.valueOf(messageLogForTTList.get("messageLogForTTList[" + i + "issue]")).equals(MessageIssueType.Other))
                    throw new Exception();
            }
        } catch (Exception e) {
            errors.add("messageLogForTTList");
        }

        //=====Сохранение данных======

        if (errors.size() == 0) {
            try {
                IncomingTT incomingTT;

                if (id_tt == 0L) { //новый IncomingTT
                    incomingTT = incomingTTService.addIncomingTT(new Date(), smppClientAccountService.getSmppClientAccountById(customer_account).getId(), subject, solution, status, customUser.getId(), customUser.getId());
                } else {           //Изменение IncomingTT
                    incomingTT = incomingTTService.updateIncomingTT(id_tt, smppClientAccountService.getSmppClientAccountById(customer_account).getId(), subject, solution, status, customUser.getId());
                }

                if (incomingTT == null) throw new Exception();

                for (int i = 0; i <= arrLastNumber; i++) {
                    MessageLogForTT messageLogForTT = null;
                    if (messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]") == null
                            || messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]").equals("")
                            || messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]").equals("0")) {
                        //новый messageLogForTT
                        messageLogForTT = messageLogForTTService.addMessageLogForIncomingTT(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]"), MessageIssueType.valueOf(messageLogForTTList.get("messageLogForTTList[" + i + "issue]")), incomingTT);
                        messageLogForTT_id_set.add(messageLogForTT.getId());
                    } else {
                        //Изменение messageLogForTT
                        messageLogForTT = messageLogForTTService.updateMessageLogForTT(Long.parseLong(messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]")), messageLogForTTList.get("messageLogForTTList[" + i + "msgid]"), MessageIssueType.valueOf(messageLogForTTList.get("messageLogForTTList[" + i + "issue]")), incomingTT, null);
                        messageLogForTT_id_set.add(messageLogForTT.getId());
                    }
                    if (messageLogForTT == null) throw new Exception();
                }

                //=====Удаление лишних messageLogForTT======

                if (incomingTT.getMessageLogForTTList() != null) {
                    for (MessageLogForTT messageLogForTT : incomingTT.getMessageLogForTTList()) {
                        if (!messageLogForTT_id_set.contains(messageLogForTT.getId()))
                            messageLogForTTService.deleteIncomingTT(messageLogForTT.getId());
                    }
                }
            } catch (Exception e) {
                errors.add("server_error");
            }
        }

        return errors;
    }

    @RequestMapping(value = "/admin_full_incoming_tt_new_email", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    HashMap<String, String> sendNewEmailIncomingTT(@RequestParam long id_tt,
                                                   @RequestParam String body_email) {
        IncomingTT incomingTT = incomingTTService.getTtById(id_tt);
        EmailContent emailContent = emailContentService.getEmailContentByIncomingTTAndEmailType(incomingTT, EmailType.Incoming);
        EmailContent newEmailContent = new EmailContent(
                EmailType.Outgoing,
                emailContent.getEmailAccount(),
                emailContent.getTo(),
                emailContent.getFrom(),
                "",
                "Re: [ITT#" + incomingTT.getId() + "] " + incomingTT.getSubject(),
                "<pre style='font-family: \"" + emailContent.getEmailAccount().getOutgoingFont().toString() + "\"; font-size: " + emailContent.getEmailAccount().getOutgoingFontSize() + "px;'>" + body_email + "</pre>" +
                        emailContent.getEmailAccount().getSignature(),
                true,
                new Date(),
                false
        );
        newEmailContent.setIncomingTT(incomingTT);
        newEmailContent = emailContentService.addEmailContentOutgoing(newEmailContent);
        HashMap<String, String> newEmail = new HashMap<>();
        newEmail.put("id_email_content", String.valueOf(newEmailContent.getId()));
        newEmail.put("body_email_content", newEmailContent.getBody());
        newEmail.put("date_email_content", newEmailContent.getDate().toGMTString());
        newEmail.put("from_email_content", newEmailContent.getFrom());
        return newEmail;
    }


    //============================ outgoing TT для NOC_SMS!!! ============================================

    @RequestMapping("/admin_full_outgoing_tt_list")
    public String adminOutgoingTTList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_outgoing_tt_list";
    }

    @RequestMapping(value = "/admin_full_get_outgoing_tt_list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<OutgoingTTXML> getOutgouingTTList() {
        return outgoingTTService.getOpenedTTXMLList();
    }

    @RequestMapping("/admin_full_outgoing_tt_history_list")
    public String adminOutgoingTTHistoryList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        Date now = new Date();
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = fd.format(now) + " 00:00:00";
        String dateEnd = fd.format(now) + " 23:59:59";
        model.addAttribute("date_start", dateStart);
        model.addAttribute("date_end", dateEnd);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customers", customerService.getCustomerAll());

        return "admin/admin_outgoing_tt_history_list";
    }

    @RequestMapping(value = "/admin_full_get_outgoing_tt_history_list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<OutgoingTTXML> getOutgoingTTHistoryList(@RequestParam Long customer_id,
                                                 @RequestParam String startDate,
                                                 @RequestParam String endDate) {
        Customer customer = null;
        if (customer_id != 0) {
            customer = customerService.getCustomerById(customer_id);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date filterStartDate = null;
        Date filterEndDate = null;
        try {
            filterStartDate = formatter.parse(startDate);
            filterEndDate = formatter.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outgoingTTService.getOpenedTTHistoryXMLList(filterStartDate, filterEndDate, customer);
    }

    @RequestMapping("/admin_full_outgoing_tt_edit")
    public String adminOutgoingTTEdit(@RequestParam long id_outgoing_tt,
                                      Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        OutgoingTT tt = null;
        SmppVendorAccount currentAccount = null;
        if (id_outgoing_tt == 0) {
            tt = new OutgoingTT();
            model.addAttribute("id_tt", 0);
        } else {
            model.addAttribute("id_tt", id_outgoing_tt);
            tt = outgoingTTService.getTtById(id_outgoing_tt);
            currentAccount = smppVendorAccountService.getSmppVendorAccountById(tt.getCustomer_account_id());
        }

        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customer", currentAccount);
        model.addAttribute("status_opened", TTStatuses.Opened);
        model.addAttribute("status_closed", TTStatuses.Closed);
        model.addAttribute("tt", tt);
        List<EmailContent> emailContentList = new ArrayList<>(tt.getEmailContentList());
        emailContentList.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        model.addAttribute("emailContentList", emailContentList);
        model.addAttribute("customers", smppVendorAccountService.getSmppVendorAccountAll());
        model.addAttribute("issueList", MessageIssueType.values());
        return "admin/admin_outgoing_tt_edit";
    }

    @RequestMapping(value = "/admin_full_outgoing_tt_new_email", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    HashMap<String, String> sendNewEmailOutgoingTT(@RequestParam long id_tt,
                                                   @RequestParam String body_email) {
        OutgoingTT outgoingTT = outgoingTTService.getTtById(id_tt);
        EmailContent emailContent = emailContentService.getEmailContentByOutgoingTTAndEmailType(outgoingTT, EmailType.Outgoing);
        EmailContent newEmailContent = new EmailContent(
                EmailType.Outgoing,
                emailContent.getEmailAccount(),
                emailContent.getFrom(),
                emailContent.getTo(),
                "",
                "[OTT#" + outgoingTT.getId() + "] " + outgoingTT.getSubject(),
                "<pre style='font-family: \"" + emailContent.getEmailAccount().getOutgoingFont().toString() + "\"; font-size: " + emailContent.getEmailAccount().getOutgoingFontSize() + "px;'>" + body_email + "</pre>" +
                        emailContent.getEmailAccount().getSignature(),
                true,
                new Date(),
                false
        );
        newEmailContent.setOutgoingTT(outgoingTT);
        newEmailContent = emailContentService.addEmailContentOutgoing(newEmailContent);
        HashMap<String, String> newEmail = new HashMap<>();
        newEmail.put("id_email_content", String.valueOf(newEmailContent.getId()));
        newEmail.put("body_email_content", newEmailContent.getBody());
        newEmail.put("date_email_content", newEmailContent.getDate().toGMTString());
        newEmail.put("from_email_content", newEmailContent.getFrom());
        return newEmail;
    }

    @RequestMapping(value = "/admin_full_save_outgoing_tt", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> saveOutgoingTT(@RequestParam Long id_tt,
                                @RequestParam TTStatuses status,
                                @RequestParam Long customer_account,
                                @RequestParam String subject,
                                @RequestParam String solution,
                                @RequestParam Map<String, String> messageLogForTTList,
                                @RequestParam Integer arrLastNumber,
                                @RequestParam Boolean first_email) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        List<String> errors = new ArrayList<>();

        //=====Проверка данных========
        if (customer_account == null || customer_account == 0L) errors.add("customer_account");
        if (smppVendorAccountService.getSmppVendorAccountById(customer_account) == null) errors.add("customer_account");

        if (subject == null || subject.equals("")) errors.add("subject");

        Set<Long> messageLogForTT_id_set = new HashSet<>();
        Set<String> msgid_set = new HashSet<>();
        try {
            for (int i = 0; i <= arrLastNumber; i++) {
                Long.parseLong(messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]"));
                if (messageLogForTTList.get("messageLogForTTList[" + i + "msgid]").equals("") || msgid_set.contains(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]")) || mdrService.getMdrByMsgId(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]")) == null)
                    throw new Exception();
                msgid_set.add(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]"));
                if (MessageIssueType.valueOf(messageLogForTTList.get("messageLogForTTList[" + i + "issue]")).equals(MessageIssueType.Other))
                    throw new Exception();
                if (!smppVendorIpsService.getSmppVendorIpsByCid(mdrService.getMdrByMsgId(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]")).getRouted_cid()).getSmppVendorAccount().equals(smppVendorAccountService.getSmppVendorAccountById(customer_account)))
                    throw new Exception();
            }
        } catch (Exception e) {
            errors.add("messageLogForTTList");
        }

        //=====Сохранение данных======

        if (errors.size() == 0) {
            try {
                OutgoingTT outgoingTT;

                if (id_tt == 0L) { //новый outgoingTT
                    outgoingTT = outgoingTTService.addOutgoingTT(new Date(), customer_account, subject, solution, status, customUser.getId(), customUser.getId());
                } else {           //Изменение outgoingTT
                    outgoingTT = outgoingTTService.updateOutgoingTT(id_tt, customer_account, subject, solution, status, customUser.getId());
                }

                if (outgoingTT == null) throw new Exception();

                for (int i = 0; i <= arrLastNumber; i++) {
                    MessageLogForTT messageLogForTT = null;
                    if (messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]") == null
                            || messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]").equals("")
                            || messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]").equals("0")) {
                        //новый messageLogForTT
                        messageLogForTT = messageLogForTTService.addMessageLogForOutgoingTT(messageLogForTTList.get("messageLogForTTList[" + i + "msgid]"), MessageIssueType.valueOf(messageLogForTTList.get("messageLogForTTList[" + i + "issue]")), outgoingTT);
                        messageLogForTT_id_set.add(messageLogForTT.getId());
                    } else {
                        //Изменение messageLogForTT
                        messageLogForTT = messageLogForTTService.updateMessageLogForTT(Long.parseLong(messageLogForTTList.get("messageLogForTTList[" + i + "messageLogForTT_id]")), messageLogForTTList.get("messageLogForTTList[" + i + "msgid]"), MessageIssueType.valueOf(messageLogForTTList.get("messageLogForTTList[" + i + "issue]")), null, outgoingTT);
                        messageLogForTT_id_set.add(messageLogForTT.getId());
                    }
                    if (messageLogForTT == null) throw new Exception();
                }

                //=====Удаление лишних messageLogForTT======

                if (outgoingTT.getMessageLogForTTList() != null) {
                    List<Long> messageLogForTT_id_list = new ArrayList<>();
                    for (MessageLogForTT messageLogForTT : outgoingTT.getMessageLogForTTList()) {
                        messageLogForTT_id_list.add(messageLogForTT.getId());
                    }
                    for (Long messageLogForTT_id : messageLogForTT_id_list) {
                        if (!messageLogForTT_id_set.contains(messageLogForTT_id))
                            messageLogForTTService.deleteOutgoingTT(messageLogForTT_id, outgoingTT);
                    }
                }

                //=========== Отправка первого письма Vendor ============

                if (first_email) {
                    EmailAccount emailAccount = emailAccountService.getAllEmailAccountByEmailRole(EmailRole.NOC_SMS).get(0);
                    StringBuilder fragTextVendorMsgid = new StringBuilder();
                    List<MessageLogForTT> messageLogForTTListEmail = new ArrayList<>(outgoingTT.getMessageLogForTTList());
                    messageLogForTTListEmail.sort((a, b) -> a.getIssueType().compareTo(b.getIssueType()));
                    MessageIssueType issueType = MessageIssueType.Other;
                    for (MessageLogForTT m : messageLogForTTListEmail) {
                        if (!m.getIssueType().equals(issueType)) {
                            fragTextVendorMsgid.append("<br>" + m.getIssueType().toString() + ":<br>");
                            issueType = m.getIssueType();
                        }
                        fragTextVendorMsgid.append(mdrService.getMdrByMsgId(m.getRelatedMsgid()).getVendor_msgid() + "<br>");
                    }
                    fragTextVendorMsgid.append(emailAccount.getSignature());
                    String body = emailAccount.getTextAutoEmailOutgoingTT().replaceFirst("\\[MessagesBlock\\]", fragTextVendorMsgid.toString());
                    EmailContent emailContent = new EmailContent(
                            EmailType.Outgoing,
                            emailAccount,
                            emailAccount.getEmail(),
                            smppVendorAccountService.getSmppVendorAccountById(customer_account).getCustomer().getSmsSupportEmail(),
                            "",
                            "[OTT#" + outgoingTT.getId() + "] " + outgoingTT.getSubject(),
                            body,
                            true,
                            new Date(),
                            false);
                    emailContent.setOutgoingTT(outgoingTT);
                    emailContentService.addEmailContentOutgoing(emailContent);
                }

            } catch (Exception e) {
                errors.add("server_error");
            }
        }
        return errors;
    }

    @RequestMapping(value = "/admin_full_create_outgoing_tt", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    List<Long> createOutgoingTT(@RequestParam Map<String, String> selectedMsgList,
                                @RequestParam Integer selectedMsgListLastNumber,
                                @RequestParam Long id_tt) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        IncomingTT incomingTT = incomingTTService.getTtById(id_tt);
        List<Long> idsOutgoingTT = new ArrayList<>();
        Map<Long, List<Long>> smppVendorAccountMap = new HashMap<>();

        for (int i = 0; i <= selectedMsgListLastNumber; i++) {
            long messageLogForTT_id = 0L;
            String msgid = null;
            MessageIssueType issue = null;
            try {
                messageLogForTT_id = Long.parseLong(selectedMsgList.get(i + "messageLogForTT_id"));
                msgid = selectedMsgList.get(i + "msgid");
                issue = MessageIssueType.valueOf(selectedMsgList.get(i + "issue"));

                if (issue.equals(MessageIssueType.Other)) throw new Exception();

                MessageLogForTT messageLogForTT = null;
                if (messageLogForTT_id == 0L) {
                    //новый messageLogForTT
                    messageLogForTT = messageLogForTTService.addMessageLogForIncomingTT(msgid, issue, incomingTT);
                } else {
                    //Изменение messageLogForTT
                    messageLogForTT = messageLogForTTService.updateMessageLogForTT(messageLogForTT_id, msgid, issue, incomingTT, null);
                }
                messageLogForTT_id = messageLogForTT.getId();
                Mdr mdr = mdrService.getMdrByMsgId(msgid);

                if (mdr == null) throw new Exception();

                SmppVendorAccount smppVendorAccount = smppVendorIpsService.getSmppVendorIpsByCid(mdr.getRouted_cid()).getSmppVendorAccount();
                if (smppVendorAccountMap.containsKey(smppVendorAccount.getId())) {
                    List<Long> msgIdList = smppVendorAccountMap.get(smppVendorAccount.getId());
                    if (!msgIdList.contains(mdr.getMsgid())) {
                        msgIdList.add(messageLogForTT_id);
                        smppVendorAccountMap.replace(smppVendorAccount.getId(), msgIdList);
                    }
                } else {
                    List<Long> msgIdList = new ArrayList<>();
                    msgIdList.add(messageLogForTT_id);
                    smppVendorAccountMap.put(smppVendorAccount.getId(), msgIdList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Map.Entry<Long, List<Long>> entry : smppVendorAccountMap.entrySet()) {
            Set<MessageLogForTT> messageLogForTTList = new HashSet<>();
            for (Long id_message : entry.getValue()) {
                messageLogForTTList.add(messageLogForTTService.getMessageLogForTTById(id_message));
            }
            OutgoingTT outgoingTT = new OutgoingTT(
                    new Date(),
                    entry.getKey(),
                    incomingTT.getSubject(),
                    "",
                    TTStatuses.Opened,
                    customUser.getId(),
                    messageLogForTTList
            );
            outgoingTT.setProcessed(false);
            outgoingTT.setIncomingTT(incomingTT);
            outgoingTTService.save(outgoingTT);
            incomingTT.addOutgoingTT(outgoingTT);
            incomingTTService.save(incomingTT);
            messageLogForTTService.addOutgoingTT(new ArrayList<>(messageLogForTTList), outgoingTT);
            idsOutgoingTT.add(outgoingTT.getId());
        }
        return idsOutgoingTT;
    }

    @RequestMapping(value = "/admin_full_get_messages_by_outgoing_tt_id", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<TTMdr> getMessagesByOutgoingTTId(@RequestParam Long tt_id) {
        List<TTMdr> TTMdrList = mdrService.getOutgoingTTMdrByTTId(tt_id);
        return TTMdrList;
    }

    //============================  Bank Account ==================================

    @RequestMapping("/admin_full_bank_account_list")
    public String adminBankList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("banks", bankAccountService.getAll());
        return "admin/admin_bank_account_list";
    }

    @RequestMapping("/admin_full_bank_account_edit")
    public String adminBankAccountEdit(@RequestParam long id_bank_account,
                                       @RequestParam String error,
                                       @RequestParam String err_name,
                                       Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        if (id_bank_account != 0) {
            model.addAttribute("bank_account", bankAccountService.getById(id_bank_account));
            model.addAttribute("bank_account_name", bankAccountService.getById(id_bank_account).getName());
        } else {
            model.addAttribute("bank_account_name", "* New account");
        }

        model.addAttribute("roles", UserRole.values());
        model.addAttribute("id_bank_account", id_bank_account);
        model.addAttribute("error", error);
        model.addAttribute("err_name", err_name);
        return "admin/admin_bank_account_edit";
    }

    @RequestMapping("/admin_full_bank_account_edit_save")
    public String adminBankAccountEditSave(
            @RequestParam long id_bank_account,
            @RequestParam String name,
            @RequestParam String holder,
            @RequestParam String account_number,
            @RequestParam String address,
            @RequestParam String iban,
            @RequestParam String swift) {

        try {
            if (id_bank_account == 0) { //новый account
                BankAccount bankAccount = new BankAccount(holder, account_number, name, address, iban, swift);
                bankAccountService.save(bankAccount);
            } else {
                BankAccount bankAccount = bankAccountService.getById(id_bank_account);
                bankAccount.setAccount_number(account_number);
                bankAccount.setHolder(holder);
                bankAccount.setName(name);
                bankAccount.setAddress(address);
                bankAccount.setIban(iban);
                bankAccount.setSwift(swift);
                bankAccountService.save(bankAccount);
            }
        } catch (Exception e) {
            return "redirect:/admin_full_bank_account_edit?id_customer=" + id_bank_account + "&error=error&err_name=";
        }
        return "redirect:/admin_full_bank_account_list";
    }


    //============================  Company   =====================================
    @RequestMapping("/admin_full_company_list")
    public String adminCompanyList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("companies", companyService.getCompanyList());
        return "admin/admin_companies_list";
    }

    @RequestMapping("/admin_full_company_edit")
    public String adminCompanyEdit(@RequestParam long id_company,
                                   @RequestParam String error,
                                   @RequestParam String err_name,
                                   Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        if (id_company != 0) {
            Company company = companyService.getCompanyById(id_company);
            model.addAttribute("company", company);
            model.addAttribute("company_name", company.getName());
            BankAccount bankAccount = bankAccountService.getById(company.getCurrentBankAccount_id());
            if (bankAccount != null) {
                model.addAttribute("bank_account_name", bankAccount.getName());
            }

        } else {
            model.addAttribute("company_name", "* New company");
        }
        model.addAttribute("bank_accounts", bankAccountService.getAll());
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("id_company", id_company);
        model.addAttribute("error", error);
        model.addAttribute("err_name", err_name);
        return "admin/admin_company_edit";
    }

    @RequestMapping("/admin_full_company_edit_save")
    public String adminCompanyEditSave(@RequestParam long id_company,
                                       @RequestParam String name,
                                       @RequestParam String address,
                                       @RequestParam String registration_number,
                                       @RequestParam String vat,
                                       @RequestParam Long currentBankAccount_id,
                                       @RequestParam String documentsPrefix) {
        try {
            if (id_company == 0) { //новый
                companyService.save(new Company(name, address, registration_number, vat, currentBankAccount_id, documentsPrefix));
            } else {
                Company company = companyService.getCompanyById(id_company);
                company.setName(name);
                company.setAddress(address);
                company.setRegistration_number(registration_number);
                company.setVat(vat);
                company.setCurrentBankAccount_id(currentBankAccount_id);
                company.setDocumentsPrefix(documentsPrefix);
                companyService.save(company);
            }
        } catch (Exception e) {
            return "redirect:/admin_full_company_edit?id_customer=" + id_company + "&error=error&err_name=";
        }
        return "redirect:/admin_full_company_list";
    }

    //============================  Billing terms =================================
    @RequestMapping("/admin_full_billing_terms_list")
    public String adminBillingTermsList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("billing_terms", smsBillingTermsService.getSmsBillingTermsAllSort());
        return "admin/admin_billing_terms_list";
    }


    @RequestMapping("/admin_full_actual_rates")
    public String adminActualRates(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("countriesList", refbookService.getRefbookAllCountrySort());
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        return "admin/admin_rate_actual";
    }

    @RequestMapping("/admin_full_rates_history")
    public String adminRatesHistory(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("clients", smppClientAccountService.getSmppClientAccountAllSort());
        model.addAttribute("vendors", smppVendorIpsService.getSmppVendorIpsAllSort());
        model.addAttribute("countriesList", refbookService.getRefbookAllCountrySort());
        return "admin/admin_rates_history";
    }


    //============================ Customer =====================================

    @RequestMapping("/admin_full_customer_list")
    public String adminCustomerList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customers", customerService.getCustomerExceptStatus(customerStatusService.getCustomerStatusByName("Invisible")));
        return "admin/admin_customer_list";
    }

    @RequestMapping("/admin_full_customer_list_invisible")
    public String adminCustomerListInvisible(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customers", customerService.getCustomerByStatus(customerStatusService.getCustomerStatusByName("Invisible")));
        return "admin/admin_customer_list_invisible";
    }

    @RequestMapping("/admin_full_customer_edit")
    public String adminUserEdit(@RequestParam long id_customer,
//                                @RequestParam String error,
//                                @RequestParam String err_name,
                                Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        if (id_customer != 0) model.addAttribute("customer", customerService.getCustomerById(id_customer));
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("id_customer", id_customer);
//        model.addAttribute("error", error);
//        model.addAttribute("err_name", err_name);
        model.addAttribute("statuses", customerStatusService.getCustomerStatusesAllSort());
        model.addAttribute("managers", userService.getUsersAllSort());
        model.addAttribute("smsBillingTermsList", smsBillingTermsService.getSmsBillingTermsAllSort());
        model.addAttribute("voiceBillingTermsList", voiceBillingTermsService.getVoiceBillingTermsAllSort());
        model.addAttribute("companies", companyService.getCompanyList());

        List<ContractTypes> contractTypes = new ArrayList<>();
        contractTypes.add(ContractTypes.NotSelected);
        contractTypes.add(ContractTypes.Bilateral);
        contractTypes.add(ContractTypes.Unilateral);

        model.addAttribute("contractTypes", contractTypes);

        if (id_customer != 0) {
            Customer currentCustomer = customerService.getCustomerById(id_customer);
            String company_name = "";
            if (currentCustomer.getCompanyId() != null && currentCustomer.getCompanyId() != 0) {
                company_name = companyService.getCompanyById(currentCustomer.getCompanyId()).getName();
            }
            model.addAttribute("signed_on", company_name);
        } else {
            model.addAttribute("signed_on", "");
        }

        return "admin/admin_customer_edit";
    }

    @RequestMapping(value = "/admin_full_customer_edit_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, Object> adminCustomerEditSave(@RequestParam long id_customer,
                                              @RequestParam String name_customer,
                                              @RequestParam CustomerStatus status_customer,
                                              @RequestParam String website_customer,
                                              @RequestParam CustomUser manager_customer,
                                              @RequestParam String fullName_customer,
                                              @RequestParam String address_customer,
                                              @RequestParam String signerName_customer,
                                              @RequestParam String signerTitle_customer,
                                              @RequestParam String phone_customer,
                                              @RequestParam String email_customer,
                                              @RequestParam String registrationNumber_customer,
                                              @RequestParam String vat_customer,
                                              @RequestParam String smsBillingTerms_customer,
                                              @RequestParam String smsSupportEmail_customer,
                                              @RequestParam String smsRateModeEmail_customer,
                                              @RequestParam String smsInvoiceEmail_customer,
                                              @RequestParam String smsDisputeEmail_customer,
                                              @RequestParam VoiceBillingTerms voiceBillingTerms_customer,
                                              @RequestParam String voiceSupportEmail_customer,
                                              @RequestParam String voiceRateModeEmail_customer,
                                              @RequestParam String voiceInvoiceEmail_customer,
                                              @RequestParam String voiceDisputeEmail_customer,
                                              @RequestParam Long signed_on,
                                              @RequestParam String dateFormat,
                                              @RequestParam String fakeName,
                                              @RequestParam ContractTypes smsContractType,
                                              @RequestParam Double smsCreditLimit,
                                              @RequestParam Double smsMinimalPayment) {

        Map<String, Object> result = new HashMap<>();
        List<String> errors = new ArrayList<>();

        if (name_customer == null || name_customer.equals("") || customerService.hasCustomer(name_customer, id_customer))
            errors.add("name_customer");

        SmsBillingTerms smsBillingTerms = null;
        try {
            if (smsBillingTerms_customer == null ||
                    smsBillingTerms_customer.equals("") ||
                    smsBillingTerms_customer.equals("0") ||
                    (smsBillingTerms = smsBillingTermsService.getSmsBillingTermsById(Long.parseLong(smsBillingTerms_customer))) == null)
                throw new Exception();
        } catch (Exception e) {
            errors.add("smsBillingTerms_customer");
        }


        Customer customer = null;
        result.put("id", "0");
        if (errors.size() == 0) {
            try {
                if (id_customer == 0) { //новый Customer
                    customer = new Customer(name_customer, address_customer, fullName_customer, signerName_customer, signerTitle_customer, phone_customer, email_customer, website_customer, registrationNumber_customer, vat_customer, manager_customer, status_customer, smsBillingTerms, voiceBillingTerms_customer, smsSupportEmail_customer, smsInvoiceEmail_customer, smsRateModeEmail_customer, smsDisputeEmail_customer, voiceSupportEmail_customer, voiceInvoiceEmail_customer, voiceRateModeEmail_customer, voiceDisputeEmail_customer);
                    customer.setCompanyId(signed_on);
                    customer.setDateFormat(dateFormat);
                    customer.setFakeName(fakeName);
                    customer.setContractType(smsContractType);
                    customer.setMinimalPayment(smsMinimalPayment);
                    customer.setCreditLimit(smsCreditLimit);
                    if ((customer = customerService.addCustomer(customer)) == null) throw new Exception();
                } else {           //Изменение Customer
                    if ((customer = customerService.updateCustomer(id_customer, name_customer, address_customer, fullName_customer, signerName_customer, signerTitle_customer, phone_customer, email_customer, website_customer, registrationNumber_customer, vat_customer, manager_customer, status_customer, smsBillingTerms, voiceBillingTerms_customer, smsSupportEmail_customer, smsInvoiceEmail_customer, smsRateModeEmail_customer, smsDisputeEmail_customer, voiceSupportEmail_customer, voiceInvoiceEmail_customer, voiceRateModeEmail_customer, voiceDisputeEmail_customer, signed_on, dateFormat, fakeName, smsContractType, smsCreditLimit, smsMinimalPayment)) == null)
                        throw new Exception();
                }
                result.put("id", customer.getId());
            } catch (Exception e) {
                errors.add("server_error");
            }
        }
        result.put("errors", errors);

        return result;
    }

    @RequestMapping(value = "/admin_full_customer_attachment_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<Map> adminCustomerAttachmentList(@RequestParam Long id){
        return customerService.getMapCustomerAttachment(id);
    }

    @RequestMapping(value = "/admin_full_customer_attachment_new/{id}", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminCustomerAttachmentNew(@RequestParam MultipartFile file,
                                            @PathVariable Long id){

        List<String> errors = new ArrayList<>();
        if (!file.isEmpty()) {
            try {
                emailAttachmentService.createAttachmentForCustomer(id, file);
            }catch (Exception e){
                errors.add("server error");
                e.printStackTrace();
            }
        }else {
            errors.add("file empty");
        }
        return errors;
    }

    @RequestMapping(value = "/admin_full_customer_attachment_delete", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminCustomerAttachmentDelete(@RequestParam Long id){
        List<String> errors = new ArrayList<>();
        if (id == null || id == 0l){
            errors.add("incorrect id");
        }else {
            try {
                emailAttachmentService.deleteAttachment(id);
            } catch (Exception e) {
                e.printStackTrace();
                errors.add("server error");
            }
        }
        return errors;
    }


    //============================  SMPP Vendor account  =====================================


    @RequestMapping("/admin_full_smpp_vendor_account_edit")
    public String adminVendorAccountEdit(@RequestParam long id_vendor_account,
                                         @RequestParam long id_customer,
                                         @RequestParam String return_in_customer,
                                         @RequestParam String error,
                                         @RequestParam String err_name,
                                         @RequestParam String err_ips,
                                         Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("id_vendor_account", id_vendor_account);
        model.addAttribute("id_customer", id_customer);
        model.addAttribute("return_in_customer", return_in_customer);
        model.addAttribute("name_customer", id_customer != 0 ? customerService.getCustomerById(id_customer).getName() : "");
        model.addAttribute("customers", customerService.getCustomerAll());
        model.addAttribute("smppVendorAccount", (id_vendor_account != 0) ? smppVendorAccountService.getSmppVendorAccountById(id_vendor_account) : new SmppVendorAccount());
        model.addAttribute("dataCodingList", DataCoding.values());
        model.addAttribute("npiList", NPI.values());
        model.addAttribute("tonList", TON.values());
        model.addAttribute("bindTypeList", BindType.values());
        model.addAttribute("softswitchList", softswitchService.getSoftswitchesAll());
        model.addAttribute("error", error);
        model.addAttribute("err_name", err_name);
        model.addAttribute("err_ips", err_ips);
        return "admin/admin_smpp_vendor_account_edit";
    }

    @RequestMapping(value = "/admin_full_smpp_vendor_account_edit_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminVendorAccountEditSave(@RequestParam long id_customer,
                                            @RequestParam long id_vendor_account,
                                            @RequestParam String name_vendor_account,
                                            @RequestParam String dont_create_dp,
                                            @RequestParam Boolean dontSync,
                                            @RequestParam String tag_vendor_account,
                                            @RequestParam Boolean ripf,
                                            @RequestParam String con_fail_delay,
                                            @RequestParam String dlr_expiry,
                                            @RequestParam DataCoding coding,
                                            @RequestParam String elink_interval,
                                            @RequestParam String bind_to,
                                            @RequestParam Boolean con_fail_retry,
                                            @RequestParam NPI bind_npi,
                                            @RequestParam TON bind_ton,
                                            @RequestParam NPI dst_npi,
                                            @RequestParam TON dst_ton,
                                            @RequestParam NPI src_npi,
                                            @RequestParam TON src_ton,
                                            @RequestParam String res_to,
                                            @RequestParam String def_msg_id,
                                            @RequestParam Integer priority,
                                            @RequestParam Boolean con_loss_retry,
                                            @RequestParam String con_loss_delay,
                                            @RequestParam String requeue_delay,
                                            @RequestParam String trx_to,
                                            @RequestParam Boolean ssl,
                                            @RequestParam BindType bind,
                                            @RequestParam Map<String, String> smppVendorIpsList,
                                            @RequestParam Integer ipsLastNumber) {
        List<String> errors = new ArrayList<>();

        //=====Проверка данных========
        if (id_customer == 0l) errors.add("id_customer");

        Customer currentCustomer = customerService.getCustomerById(id_customer);

        if (name_vendor_account == null || name_vendor_account.equals("") || smppVendorAccountService.isName(id_vendor_account, name_vendor_account, currentCustomer))
            errors.add("name_vendor_account");

        boolean dont_create_dp_boolean = true;
        if (dont_create_dp == null || dont_create_dp.equals("") || dont_create_dp.equals("false"))
            dont_create_dp_boolean = false;

        Integer tag_vendor_account_integer = null;
        try {
            if (tag_vendor_account == null || tag_vendor_account.equals("")) {
                tag_vendor_account_integer = null;
            } else {
                tag_vendor_account_integer = Integer.parseInt(tag_vendor_account);
                if (smppVendorAccountService.isTag(id_vendor_account, tag_vendor_account_integer))
                    throw new Exception();
            }
        } catch (Exception e) {
            errors.add("tag_vendor_account");
        }

        int con_fail_delay_int = 0;
        try {
            con_fail_delay_int = Integer.parseInt(con_fail_delay);
        } catch (Exception e) {
            errors.add("con_fail_delay");
        }

        int dlr_expiry_int = 0;
        try {
            dlr_expiry_int = Integer.parseInt(dlr_expiry);
        } catch (Exception e) {
            errors.add("dlr_expiry");
        }

        int elink_interval_int = 0;
        try {
            elink_interval_int = Integer.parseInt(elink_interval);
        } catch (Exception e) {
            errors.add("elink_interval");
        }

        int bind_to_int = 0;
        try {
            bind_to_int = Integer.parseInt(bind_to);
        } catch (Exception e) {
            errors.add("bind_to");
        }

        int res_to_int = 0;
        try {
            res_to_int = Integer.parseInt(res_to);
        } catch (Exception e) {
            errors.add("res_to");
        }

        int def_msg_id_int = 0;
        try {
            def_msg_id_int = Integer.parseInt(def_msg_id);
        } catch (Exception e) {
            errors.add("def_msg_id");
        }

        int con_loss_delay_int = 0;
        try {
            con_loss_delay_int = Integer.parseInt(con_loss_delay);
        } catch (Exception e) {
            errors.add("con_loss_delay");
        }

        int requeue_delay_int = 0;
        try {
            requeue_delay_int = Integer.parseInt(requeue_delay);
        } catch (Exception e) {
            errors.add("requeue_delay");
        }

        int trx_to_int = 0;
        try {
            trx_to_int = Integer.parseInt(trx_to);
        } catch (Exception e) {
            errors.add("trx_to");
        }

        try {
            Set<String> cids = new HashSet<>();
            Boolean selectFlag = null;
            for (int i = 0; i <= ipsLastNumber; i++) {
                long id = 0l;
                if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][id]") != null && !smppVendorIpsList.get("smppVendorIpsList[" + i + "][id]").equals("")) {
                    id = Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][id]"));
                }
                if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][ip]") == null || smppVendorIpsList.get("smppVendorIpsList[" + i + "][ip]").equals(""))
                    throw new Exception();
                Integer.parseInt(smppVendorIpsList.get("smppVendorIpsList[" + i + "][port]"));
                if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][systemId]") == null || smppVendorIpsList.get("smppVendorIpsList[" + i + "][systemId]").equals(""))
                    throw new Exception();
                if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][password]") == null || smppVendorIpsList.get("smppVendorIpsList[" + i + "][password]").equals(""))
                    throw new Exception();
                Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][submitThroughput]"));
                Boolean.parseBoolean(smppVendorIpsList.get("smppVendorIpsList[" + i + "][allowed]"));
                if ((smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]") != null
                        && !smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]").equals("")
                        && smppVendorIpsService.isCid(id, smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]")))
                        || (smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]") != null
                        && cids.contains(smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]")))) {
                    throw new Exception();
                }
                if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]") != null && !smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]").equals("")) {
                    cids.add(smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]"));
                }
                if (selectFlag == null) {
                    if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]") == null || smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]").equals("")) {
                        selectFlag = false;
                    } else {
                        selectFlag = Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]")) > 0;
                    }
                } else {
                    if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]") == null || smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]").equals("") || smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]") == null || smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]").equals("0")) {
                        if (selectFlag) throw new Exception();
                    } else {
                        Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]"));
                        if (!selectFlag) throw new Exception();
                    }
                }
            }
        } catch (Exception e) {
            errors.add("ips_list");
        }

        //=====Сохранение данных======

        if (errors.size() == 0) {
            try {
                SmppVendorAccount smppVendorAccount;

                if (id_vendor_account == 0) { //новый Vendor Account
                    smppVendorAccount = smppVendorAccountService.addSmppVendorAccount(name_vendor_account, dont_create_dp_boolean, customerService.getCustomerById(id_customer), tag_vendor_account_integer, ripf, con_fail_delay_int, dlr_expiry_int, coding, elink_interval_int, bind_to_int, con_fail_retry, bind_npi, bind_ton, dst_npi, dst_ton, src_npi, src_ton, res_to_int, def_msg_id_int, priority, con_loss_retry, con_loss_delay_int, requeue_delay_int, trx_to_int, ssl, bind, dontSync);
                    if (smppVendorAccount == null) throw new Exception();
                } else {           //Изменение Vendor Account
                    smppVendorAccount = smppVendorAccountService.updateSmppVendorAccount(id_vendor_account, name_vendor_account, dont_create_dp_boolean, customerService.getCustomerById(id_customer), tag_vendor_account_integer, ripf, con_fail_delay_int, dlr_expiry_int, coding, elink_interval_int, bind_to_int, con_fail_retry, bind_npi, bind_ton, dst_npi, dst_ton, src_npi, src_ton, res_to_int, def_msg_id_int, priority, con_loss_retry, con_loss_delay_int, requeue_delay_int, trx_to_int, ssl, bind, dontSync);
                    if (smppVendorAccount == null) throw new Exception();
                }

                Set<Long> idsIps = new HashSet<>();
                for (int i = 0; i <= ipsLastNumber; i++) {
                    if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][id]") == null
                            || smppVendorIpsList.get("smppVendorIpsList[" + i + "][id]").equals("")
                            || smppVendorIpsList.get("smppVendorIpsList[" + i + "][id]").equals("0")) {                    //новый Vendor Ips
                        Long softswitchId = null;
                        if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]") != null && !smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]").equals("")) {
                            softswitchId = Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]"));
                        }
                        idsIps.add(smppVendorIpsService.addSmppVendorIps(new SmppVendorIps(smppVendorIpsList.get("smppVendorIpsList[" + i + "][ip]"),
                                Integer.parseInt(smppVendorIpsList.get("smppVendorIpsList[" + i + "][port]")),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][systemId]"),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][password]"),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][systemType]"),
                                Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][submitThroughput]")),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]"),
                                Boolean.parseBoolean(smppVendorIpsList.get("smppVendorIpsList[" + i + "][allowed]")),
                                smppVendorAccount,
                                softswitchId)).getId());
                    } else {                                                                                                          //Изменение Vendor Ips
                        Long softswitchId = null;
                        if (smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]") != null && !smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]").equals("")) {
                            softswitchId = Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][softswitchId]"));
                        }
                        idsIps.add(smppVendorIpsService.updateSmppVendorIps(Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][id]")),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][ip]"),
                                Integer.parseInt(smppVendorIpsList.get("smppVendorIpsList[" + i + "][port]")),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][systemId]"),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][password]"),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][systemType]"),
                                Long.parseLong(smppVendorIpsList.get("smppVendorIpsList[" + i + "][submitThroughput]")),
                                smppVendorIpsList.get("smppVendorIpsList[" + i + "][cid]"),
                                Boolean.parseBoolean(smppVendorIpsList.get("smppVendorIpsList[" + i + "][allowed]")),
                                smppVendorAccount,
                                softswitchId).getId());
                    }
                }

                //=====Удаление лишних IP======

                if (smppVendorAccount.getSmppVendorIpsList() != null) {
                    for (SmppVendorIps smppVendorIps : smppVendorAccount.getSmppVendorIpsList()) {
                        if (!idsIps.contains(smppVendorIps.getId()))
                            smppVendorIpsService.deleteSmppVendorIps(smppVendorIps.getId());
                    }
                }
            } catch (Exception e) {
                errors.add("server_error");
            }
        }
        return errors;
    }

    @RequestMapping("/admin_full_all_smpp_vendor_account_list")
    public String adminAllSmppVendorAccountList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("smppVendorAccountList", smppVendorAccountService.getSmppVendorAccountAll());
        return "admin/admin_all_smpp_vendor_account_list";
    }


    //============================  SMPP Client account  =====================================


    @RequestMapping("/admin_full_smpp_client_account_edit")
    public String adminClientAccountEdit(@RequestParam long id_client_account,
                                         @RequestParam long id_customer,
                                         @RequestParam String return_in_customer,
                                         @RequestParam String error,
                                         @RequestParam String err_name,
                                         @RequestParam String err_ips,
                                         @RequestParam String err_si,
                                         Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("id_client_account", id_client_account);
        model.addAttribute("id_customer", id_customer);
        model.addAttribute("return_in_customer", return_in_customer);
        model.addAttribute("name_customer", id_customer != 0 ? customerService.getCustomerById(id_customer).getName() : "");
        model.addAttribute("customers", customerService.getCustomerAll());
        model.addAttribute("smppClientAccount", smppClientAccountService.getSmppClientAccountById(id_client_account));
        model.addAttribute("error", error);
        model.addAttribute("err_name", err_name);
        model.addAttribute("err_ips", err_ips);
        model.addAttribute("err_si", err_si);
        return "admin/admin_smpp_client_account_edit";
    }

    @RequestMapping(value = "/admin_full_smpp_client_account_edit_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminClientAccountEditSave(@RequestParam long id_customer,
                                            @RequestParam long id_client_account,
                                            @RequestParam String name_client_account,
                                            @RequestParam boolean dontSync,
                                            @RequestParam String system_type_client_account,
                                            @RequestParam Integer ipsLastNumber,
                                            @RequestParam Integer systemIdLastNumber,
                                            @RequestParam HashMap<String, String> dataMap) {
        List<String> errors = new ArrayList<>();

        //=====Проверка данных========

        if (id_customer == 0l) errors.add("id_customer");

        Customer currentCustomer = customerService.getCustomerById(id_customer);

        if (name_client_account == null || name_client_account.equals("") || smppClientAccountService.isName(id_client_account, name_client_account, currentCustomer))
            errors.add("name_client_account");

        try {
            for (int i = 0; i <= ipsLastNumber; i++) {
                if (dataMap.get("smppClientIpsList[" + i + "][ip]") == null || dataMap.get("smppClientIpsList[" + i + "][ip]").equals(""))
                    throw new Exception();
                Boolean.parseBoolean(dataMap.get("smppClientIpsList[" + i + "][allowed]"));
            }
        } catch (Exception e) {
            errors.add("ips_list");
        }

        try {
            Set<String> uids = new HashSet<>();
            for (int i = 0; i <= systemIdLastNumber; i++) {
                long id = 0l;
                if (dataMap.get("smppClientSystemIdList[" + i + "][id]") != null && !dataMap.get("smppClientSystemIdList[" + i + "][id]").equals("")) {
                    id = Long.parseLong(dataMap.get("smppClientSystemIdList[" + i + "][id]"));
                }
                if (dataMap.get("smppClientSystemIdList[" + i + "][systemId]") == null
                        || dataMap.get("smppClientSystemIdList[" + i + "][systemId]").equals("")
                        || smppClientSystemIdService.isSystemId(id, dataMap.get("smppClientSystemIdList[" + i + "][systemId]"))) {
                    throw new Exception();
                }
                if (dataMap.get("smppClientSystemIdList[" + i + "][password]") == null
                        || dataMap.get("smppClientSystemIdList[" + i + "][password]").equals("")) {
                    throw new Exception();
                }
                if ((dataMap.get("smppClientSystemIdList[" + i + "][uid]") != null
                        && !dataMap.get("smppClientSystemIdList[" + i + "][uid]").equals("")
                        && smppClientSystemIdService.isUid(id, dataMap.get("smppClientSystemIdList[" + i + "][uid]")))
                        || (dataMap.get("smppClientSystemIdList[" + i + "][uid]") != null
                        && uids.contains(dataMap.get("smppClientSystemIdList[" + i + "][uid]")))) {
                    throw new Exception();
                }
                if (dataMap.get("smppClientSystemIdList[" + i + "][uid]") != null && !dataMap.get("smppClientSystemIdList[" + i + "][uid]").equals("")) {
                    uids.add(dataMap.get("smppClientSystemIdList[" + i + "][uid]"));
                }
            }
        } catch (Exception e) {
            errors.add("systemId_list");
        }

        //=====Сохранение данных======
        if (errors.size() == 0) {
            try {
                SmppClientAccount smppClientAccount;
                if (id_client_account == 0) { //новый Client Account
                    smppClientAccount = smppClientAccountService.addSmppClientAccount(name_client_account, system_type_client_account, 0l, customerService.getCustomerById(id_customer), dontSync);
                    if (smppClientAccount == null) throw new Exception();
                } else {           //Изменение Client Account
                    smppClientAccount = smppClientAccountService.updateSmppClientAccount(id_client_account, name_client_account, system_type_client_account, 0l, customerService.getCustomerById(id_customer), dontSync);
                    if (smppClientAccount == null) throw new Exception();
                }

                Set<Long> idsIps = new HashSet<>();
                for (int i = 0; i <= ipsLastNumber; i++) {
                    if (dataMap.get("smppClientIpsList[" + i + "][id]") == null
                            || dataMap.get("smppClientIpsList[" + i + "][id]").equals("")
                            || dataMap.get("smppClientIpsList[" + i + "][id]").equals("0")) {
                        idsIps.add(smppClientIpsService.addSmppClientIps(new SmppClientIps(dataMap.get("smppClientIpsList[" + i + "][ip]"),
                                Boolean.parseBoolean(dataMap.get("smppClientIpsList[" + i + "][allowed]")),
                                smppClientAccount)).getId());
                    } else {
                        idsIps.add(smppClientIpsService.updateSmppClientIps(Long.parseLong(dataMap.get("smppClientIpsList[" + i + "][id]")),
                                dataMap.get("smppClientIpsList[" + i + "][ip]"),
                                Boolean.parseBoolean(dataMap.get("smppClientIpsList[" + i + "][allowed]")),
                                smppClientAccount).getId());
                    }
                }

                //=====Удаление лишних IP======

                if (smppClientAccount.getSmppClientIpsList() != null) {
                    for (SmppClientIps smppClientIps : smppClientAccount.getSmppClientIpsList()) {
                        if (!idsIps.contains(smppClientIps.getId()))
                            smppClientIpsService.deleteSmppClientIps(smppClientIps.getId());
                    }
                }

                Set<Long> idsSystemId = new HashSet<>();
                for (int i = 0; i <= systemIdLastNumber; i++) {
                    if (dataMap.get("smppClientSystemIdList[" + i + "][id]") == null
                            || dataMap.get("smppClientSystemIdList[" + i + "][id]").equals("")
                            || dataMap.get("smppClientSystemIdList[" + i + "][id]").equals("0")) {
                        idsSystemId.add(smppClientSystemIdService.addSmppCleintSystemId(dataMap.get("smppClientSystemIdList[" + i + "][systemId]"),
                                dataMap.get("smppClientSystemIdList[" + i + "][password]"),
                                dataMap.get("smppClientSystemIdList[" + i + "][uid]"),
                                smppClientAccount).getId());
                    } else {
                        idsSystemId.add(smppClientSystemIdService.updateSmppCleintSystemId(Long.parseLong(dataMap.get("smppClientSystemIdList[" + i + "][id]")),
                                dataMap.get("smppClientSystemIdList[" + i + "][systemId]"),
                                dataMap.get("smppClientSystemIdList[" + i + "][password]"),
                                dataMap.get("smppClientSystemIdList[" + i + "][uid]")).getId());
                    }
                }

                //=====Удаление лишних SystemId======

                if (smppClientAccount.getSmppClientSystemIdList() != null) {
                    for (SmppClientSystemId smppClientSystemId : smppClientAccount.getSmppClientSystemIdList()) {
                        if (!idsSystemId.contains(smppClientSystemId.getId()))
                            smppClientSystemIdService.deleteSmppCleintSystemId(smppClientSystemId.getId());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                errors.add("server_error");
            }
        }
        return errors;
    }

    @RequestMapping("/admin_full_all_smpp_client_account_list")
    public String adminAllSmppClientAccountList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("smppClientAccountList", smppClientAccountService.getSmppClientAccountAll());
        return "admin/admin_all_smpp_client_account_list";
    }


    //========================= Softswitch  ==============================


    @RequestMapping("/admin_full_softswitch_list")
    public String adminSoftswitchList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("softswitches", softswitchService.getSoftswitchesAll());
        return "admin/admin_all_softswitch_list";
    }

    @RequestMapping("/admin_full_softswitch_edit")
    public String adminSoftswitchEdit(@RequestParam long id_softswitch,
                                      @RequestParam String error,
                                      //@RequestParam String err_name,
                                      Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("types", SoftswitchType.values());
        if (id_softswitch != 0) {
            model.addAttribute("softswitch", softswitchService.getSoftswitchById(id_softswitch));
            SoftswitchTriggers mdr_load_enabledTrigger = softswitchTriggersService.getTriggersBySoftswitchIdAndKey(id_softswitch, "mdr_load_enabled");
            SoftswitchTriggers api_exchange_enabledTrigger = softswitchTriggersService.getTriggersBySoftswitchIdAndKey(id_softswitch, "api_exchange_enabled");
            ;
            if (mdr_load_enabledTrigger != null) {
                model.addAttribute("mdr_load_enabled", mdr_load_enabledTrigger.getValue());
            } else {
                model.addAttribute("mdr_load_enabled", false);
            }
            if (api_exchange_enabledTrigger != null) {
                model.addAttribute("api_exchange_enabled", api_exchange_enabledTrigger.getValue());
            } else {
                model.addAttribute("api_exchange_enabled", false);
            }
        }
        model.addAttribute("id_softswitch", id_softswitch);
        model.addAttribute("error", error);
        //model.addAttribute("err_name", err_name);
        return "admin/admin_softswitch_edit";
    }

    @RequestMapping(value = "/admin_full_softswitch_edit_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminUserEditSave(@RequestParam long id_softswitch,
                                   @RequestParam String name,
                                   @RequestParam String type,
                                   @RequestParam String host,
                                   @RequestParam String mysql_port,
                                   @RequestParam String mysql_database,
                                   @RequestParam String mysql_username,
                                   @RequestParam String mysql_password,
                                   @RequestParam String mysql_dr_table,
                                   @RequestParam String mysql_totals_table,
                                   @RequestParam String mysql_stats_table,
                                   @RequestParam String mysql_reports_table,
                                   @RequestParam boolean mdr_load_enabled,
//                                    @RequestParam boolean sms_totals_load_enabled,
                                   @RequestParam boolean api_exchange_enabled,
                                   @RequestParam String api_port,
                                   @RequestParam String api_username,
                                   @RequestParam String api_password) {

        List<String> errors = new ArrayList<>();

        //=====Проверка данных========

        SoftswitchType softswitch_type = null;
        try {
            softswitch_type = SoftswitchType.valueOf(type);
        } catch (Exception e) {
            errors.add("type");
            e.printStackTrace();
        }

        int mysql_port_int = 0;
        try {
            mysql_port_int = Integer.parseInt(mysql_port);
        } catch (Exception e) {
            errors.add("mysql_port");
            e.printStackTrace();
        }

        int api_port_int = 0;
        try {
            api_port_int = Integer.parseInt(api_port);
        } catch (Exception e) {
            errors.add("api_port");
            e.printStackTrace();
        }

        if (errors.size() == 0) {
            try {
                if (id_softswitch == 0) { //новый Softswitch
                    Softswitch softswitch = new Softswitch(name, softswitch_type, host, mysql_port_int, mysql_database, mysql_username, mysql_password, mysql_dr_table, mysql_totals_table, mysql_stats_table, mysql_reports_table, api_port_int, api_username, api_password);
                    if (!softswitchService.addSoftswitch(softswitch)) {
                        throw new Exception();
                    } else {
                        id_softswitch = softswitch.getId();
                    }
                } else {           //Изменение Softswitch
                    if (!softswitchService.updateSoftswitch(id_softswitch, name, softswitch_type, host, mysql_port_int, mysql_database, mysql_username, mysql_password, mysql_dr_table, mysql_totals_table, mysql_stats_table, mysql_reports_table, api_port_int, api_username, api_password))
                        throw new Exception();
                }
                softswitchTriggersService.updateSoftswitchTriggers(id_softswitch, "mdr_load_enabled", mdr_load_enabled);
                softswitchTriggersService.updateSoftswitchTriggers(id_softswitch, "api_exchange_enabled", api_exchange_enabled);
            } catch (Exception e) {
                errors.add("server_error");
            }
        }
        return errors;
    }

    @RequestMapping("/admin_full_global_settings")
    public String adminGlobalSettingEdit(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());

        ///////Bot enabled
        Boolean enableBot = false;
        Settings botEnableed = settingsService.getSettingByKey("global_bot_enabled");
        if (botEnableed != null) {
            String value = botEnableed.getValue();
            if (value.equals("true")) {
                enableBot = true;
            }
        }
        model.addAttribute("enableBot", enableBot);


        ///////Bot API key
        String botApiKey = "";
        Settings botApiKeySetting = settingsService.getSettingByKey("global_bot_api_key");
        if (botApiKeySetting != null) {
            botApiKey = botApiKeySetting.getValue();
        }
        model.addAttribute("botApiKey", botApiKey);


        ///////Bot Alert Chat id
        String botAlertChatId = "";
        Settings botAlertChatIdSetting = settingsService.getSettingByKey("global_bot_alert_chat_id");
        if (botAlertChatIdSetting != null) {
            botAlertChatId = botAlertChatIdSetting.getValue();
        }
        model.addAttribute("botAlertChatId", botAlertChatId);


        ///////Bot System Chat id
        String botSystemChatId = "";
        Settings botSystemChatIdSetting = settingsService.getSettingByKey("global_bot_system_chat_id");
        if (botSystemChatIdSetting != null) {
            botSystemChatId = botSystemChatIdSetting.getValue();
        }
        model.addAttribute("botSystemChatId", botSystemChatId);

        ///////Bot NOC Chat id
        String botNOCChatId = "";
        Settings botNOCChatIdSetting = settingsService.getSettingByKey("global_noc_system_chat_id");
        if (botNOCChatIdSetting != null) {
            botNOCChatId = botNOCChatIdSetting.getValue();
        }
        model.addAttribute("botNOCChatId", botNOCChatId);


        ///////Bot NOC Chat id
        String siteHeader = "";
        Settings siteHeaderSetting = settingsService.getSettingByKey("global_site_header");
        if (siteHeaderSetting != null) {
            siteHeader = siteHeaderSetting.getValue();
        }
        model.addAttribute("siteHeader", siteHeader);


        return "admin/admin_global_settings";
    }

    @RequestMapping("/admin_full_global_settings_save")
    public String adminGlobalSettingSave(@RequestParam(required = false) String enableBot,
                                         @RequestParam String botApiKey,
                                         @RequestParam String botAlertChatId,
                                         @RequestParam String botSystemChatId,
                                         @RequestParam String botNOCChatId,
                                         @RequestParam String siteHeader,
                                         Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Boolean enableBotValue = false;
        if (enableBot != null) {
            enableBotValue = true;
        }

        Settings enableBotSetting = settingsService.getSettingByKey("global_bot_enabled");
        if (enableBotSetting != null) {
            enableBotSetting.setValue(enableBotValue.toString());
        } else {
            enableBotSetting = new Settings("global_bot_enabled", enableBotValue.toString());
        }
        settingsService.save(enableBotSetting);

        Settings botApiKeySetting = settingsService.getSettingByKey("global_bot_api_key");
        if (botApiKeySetting != null) {
            botApiKeySetting.setValue(botApiKey);
        } else {
            botApiKeySetting = new Settings("global_bot_api_key", botApiKey);
        }
        settingsService.save(botApiKeySetting);

        Settings botAlertChatIdSetting = settingsService.getSettingByKey("global_bot_alert_chat_id");
        if (botAlertChatIdSetting != null) {
            botAlertChatIdSetting.setValue(botAlertChatId);
        } else {
            botAlertChatIdSetting = new Settings("global_bot_alert_chat_id", botAlertChatId);
        }
        settingsService.save(botAlertChatIdSetting);

        Settings botSystemChatIdSetting = settingsService.getSettingByKey("global_bot_system_chat_id");
        if (botSystemChatIdSetting != null) {
            botSystemChatIdSetting.setValue(botSystemChatId);
        } else {
            botSystemChatIdSetting = new Settings("global_bot_system_chat_id", botSystemChatId);
        }
        settingsService.save(botSystemChatIdSetting);

        Settings botNOCChatIdSetting = settingsService.getSettingByKey("global_noc_system_chat_id");
        if (botNOCChatIdSetting != null) {
            botNOCChatIdSetting.setValue(botNOCChatId);
        } else {
            botNOCChatIdSetting = new Settings("global_noc_system_chat_id", botNOCChatId);
        }
        settingsService.save(botNOCChatIdSetting);

        Settings siteHeaderSetting = settingsService.getSettingByKey("global_site_header");
        if (siteHeaderSetting != null) {
            siteHeaderSetting.setValue(siteHeader);
        } else {
            siteHeaderSetting = new Settings("global_site_header", siteHeader);
        }
        settingsService.save(siteHeaderSetting);

        return "redirect:/admin_full_global_settings";
    }


    //============================  Mdr  =============================


    @RequestMapping("/admin_full_mdr")
    public String adminMdr(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        Date now = new Date();
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = fd.format(now) + " 00:00:00";
        String dateEnd = fd.format(now) + " 23:59:59";
        model.addAttribute("date_start", dateStart);
        model.addAttribute("date_end", dateEnd);
        model.addAttribute("smppClientAccountList", smppClientAccountService.getSmppClientAccountAll());
        model.addAttribute("smppVendorAccountList", smppVendorAccountService.getSmppVendorAccountAll());
        model.addAttribute("refbooks", refbookService.getRefbookAllCountrySort());

        return "admin/admin_mdr";
    }


    // Report SOA
    @RequestMapping("/admin_full_soa")
    public String adminSoa(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        Date now = new Date();
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = fd.format(now) + " 00:00:00";
        String dateEnd = fd.format(now) + " 23:59:59";
        model.addAttribute("date_start", dateStart);
        model.addAttribute("date_end", dateEnd);
        model.addAttribute("customers", customerService.getCustomerAllSort());

        return "admin/admin_statements_of_accounts";
    }

    // Report Balances
    @RequestMapping("/admin_full_sms_balances")
    public String adminBalances(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customers", customerService.getCustomerAllSort());

        return "admin/admin_sms_balances";
    }

    //==============================Invoices =============================================

    //==============================Outgoing invoice =====================================

    @RequestMapping("/admin_full_sms_outgoing_invoice_list")
    public String adminOutgoingInvoiceList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("customers", customerService.getCustomerAllSort());
        List<OutgoingInvoice> invoiceList = outgoingInvoiceService.getInvoicesSort();
        List<OutgoingInvoiceRow> list = new ArrayList<>();

        for (OutgoingInvoice currentInvoice : invoiceList) {

            String customerName = "";
            String invoiceNumber = "";


            Customer customer = customerService.getCustomerById(currentInvoice.getCustomer_id());
            customerName = customer.getName();
            if (customer.getCompanyId() != null) {
                Company company = companyService.getCompanyById(customer.getCompanyId());
                if (company != null) {
                    invoiceNumber = invoiceNumber + company.getDocumentsPrefix() + currentInvoice.getId();
                }
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.format(currentInvoice.getStartDate());

            list.add(new OutgoingInvoiceRow(currentInvoice.getId(), invoiceNumber, customerName, df.format(currentInvoice.getDate()), smsBillingTermsService.getSmsBillingTermsById(currentInvoice.getBillingPeriodId()).getName(),
                    df.format(currentInvoice.getStartDate()), df.format(currentInvoice.getEndDate()), df.format(currentInvoice.getPayDate()), currentInvoice.getSent_to(), currentInvoice.getInvoiceSum()));
        }


        model.addAttribute("invoices", list);

        return "admin/admin_sms_outgoing_invoice_list";
    }

    @RequestMapping("/admin_full_sms_outgoing_invoice_edit")
    public String adminOutgoinginvoiceEdit(@RequestParam long id_invoice,
//                                           @RequestParam String error,
//                                           @RequestParam String err_name,
                                           Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name_admin", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("id_invoice", id_invoice);
//        model.addAttribute("error", error);
//        model.addAttribute("err_name", err_name);

        if (id_invoice != 0) {
            OutgoingInvoice invoice = outgoingInvoiceService.getInvoiceById(id_invoice);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(invoice.getDate());
            model.addAttribute("invoice_date", date);

            date = format.format(invoice.getStartDate());
            model.addAttribute("invoice_billing_period_start", date);

            date = format.format(invoice.getEndDate());
            model.addAttribute("invoice_billing_period_end", date);

            date = format.format(invoice.getPayDate());
            model.addAttribute("invoice_billing_period_pay", date);

            Customer invoiceCustomer = customerService.getCustomerById(invoice.getCustomer_id());
            Long companyId = invoiceCustomer.getCompanyId();

            if (companyId != null) {
                Company company = companyService.getCompanyById(companyId);
                if (company != null) {
                    model.addAttribute("invoice_number", company.getDocumentsPrefix() + invoice.getId());
                }
            }

            model.addAttribute("invoice_customer_name", invoiceCustomer.getName());
            model.addAttribute("invoice_billing_period_name", smsBillingTermsService.getSmsBillingTermsById(invoice.getBillingPeriodId()).getName());
            model.addAttribute("invoice_sum", invoice.getInvoiceSum());
            model.addAttribute("invoice_sent_to", invoice.getSent_to());
            model.addAttribute("invoice_confirmed", invoice.getConfirmed());
            model.addAttribute("detailsList", outgoingInvoiceTrafficDetailsService.getTrafficDetailsRowList(id_invoice));
            model.addAttribute("attachmentList", invoice.getEmailAttachmentList());
            model.addAttribute("emailContentList", invoice.getEmailContents());
        }
        return "admin/admin_outgoing_invoice_edit";
    }

    @RequestMapping(value = "/admin_full_sms_outgoing_invoice_confirmed", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminOutgoingInvoiceConfirmed(@RequestParam long id_invoice,
                                               @RequestParam Boolean confirmed) {
        List<String> errors = new ArrayList<>();
        try {
            outgoingInvoiceService.updateConfirmed(id_invoice, confirmed);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("server error");
        }
        return errors;
    }


    @RequestMapping(value = "/admin_full_recalculate_outgoing_invoice", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Response recalculateOutgoingInvoice(@RequestParam Long invoice_id) {
        Response response = new Response();
        response.setStatus("False");
        OutgoingInvoice invoice = outgoingInvoiceService.getInvoiceById(invoice_id);
        if (invoice != null) {
            Soa currentSoa = soaService.getSoaByPeriod(invoice.getStartDate(), invoice.getEndDate(), invoice.getCustomer_id(), invoice.getBillingPeriodId(), DebitCredit.Debit, FinanceEvents.Outgoing_Invoice);
            if (currentSoa != null) {
                cachesUpdateTask.updateOutgoingInvoice(invoice, invoice.getCustomer_id(), currentSoa, cachesUpdateTask.getUidsListByCustomerId(invoice.getCustomer_id()));
            }
        }
        response.setStatus("True");

        return response;
    }


    //==============================Messaging Flow =======================================


    @RequestMapping("/admin_full_messaging_flow")
    public String adminMessagingFlow(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("vendorDialpeerList", vendorDialpeerService.getVendorDialpeersAll());
        return "admin/admin_messaging_flow";
    }


    //============================  Vendor Dialpeer  =====================================


    @RequestMapping("/admin_full_vendor_dialpeer_list")
    public String adminVendorDialpeerList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("vendorDialpeerList", vendorDialpeerService.getVendorDialpeersAll());
        return "admin/admin_vendor_dialpeer_list";
    }

    @RequestMapping("/admin_full_vendor_dialpeer_edit")
    public String adminVendorDialpeerEdit(@RequestParam long id_vendor_dialpeer,
//                                          @RequestParam String error,
                                          Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        if (id_vendor_dialpeer != 0)
            model.addAttribute("vendorDialpeer", vendorDialpeerService.getVendorDialpeerById(id_vendor_dialpeer));
        model.addAttribute("id_vendor_dialpeer", id_vendor_dialpeer);
//        model.addAttribute("error", error);
        model.addAttribute("smppVendorIpsList", smppVendorIpsService.getSmppVendorIpsAllSort());
        return "admin/admin_vendor_dialpeer_edit";
    }

    @RequestMapping(value = "/admin_full_vendor_dialpeer_edit_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminVendorDialpeerEditSave(@RequestParam long id_vendor_dialpeer,
                                             @RequestParam String name_vendor_dialpeer,
                                             @RequestParam Map<String, String> vendorDialpeerChildList,
                                             @RequestParam Integer vendorDialpeerChildLastNumber) {
        List<String> errors = new ArrayList<>();

        //=====Проверка данных========
        if (name_vendor_dialpeer == null || name_vendor_dialpeer.equals(""))
            errors.add("name_vendor_dialpeer");

        try {
            Set<String> ids = new HashSet<>();
            Boolean selectFlag = null;
            for (int i = 0; i <= vendorDialpeerChildLastNumber; i++) {
                long id = 0l;
                if (vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id]") != null && !vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id]").equals("")) {
                    id = Long.parseLong(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id]"));
                }
                if (smppVendorIpsService.getSmppVendorIpsById(Long.parseLong(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id_smppVendorIps]"))) == null) {
                    throw new Exception();
                }
                Integer.parseInt(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][weight]"));
            }
        } catch (Exception e) {
            errors.add("vendorDialpeerChildList");
        }

        //=====Сохранение данных======

        if (errors.size() == 0) {
            try {
                VendorDialpeer vendorDialpeer;

                if (id_vendor_dialpeer == 0) { //новый Vendor Dialpeer
                    vendorDialpeer = vendorDialpeerService.addVendorDialpeer(name_vendor_dialpeer);
                    if (vendorDialpeer == null) throw new Exception();
                } else {           //Изменение Vendor Dialpeer
                    vendorDialpeer = vendorDialpeerService.updateVendorDialpeer(id_vendor_dialpeer, name_vendor_dialpeer);
                    if (vendorDialpeer == null) throw new Exception();
                }

                Set<Long> idsVendorDialpeerChild = new HashSet<>();
                for (int i = 0; i <= vendorDialpeerChildLastNumber; i++) {
                    if (vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id]") == null
                            || vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id]").equals("")
                            || vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id]").equals("0")) {                    //новый Vendor Dialpeer
                        idsVendorDialpeerChild.add(vendorDialpeerChildService.addVendorDialpeerChild(
                                Integer.parseInt(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][weight]")),
                                smppVendorIpsService.getSmppVendorIpsById(Long.parseLong(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id_smppVendorIps]"))),
                                vendorDialpeer));
                    } else {                                                                                                          //Изменение Vendor Dialpeer
                        idsVendorDialpeerChild.add(vendorDialpeerChildService.updateVendorDialpeerChild(
                                Long.parseLong(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id]")),
                                Integer.parseInt(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][weight]")),
                                smppVendorIpsService.getSmppVendorIpsById(Long.parseLong(vendorDialpeerChildList.get("vendorDialpeerChildList[" + i + "][id_smppVendorIps]")))));
                    }
                }

                //=====Удаление лишних vendorDialpeerChild======

                if (vendorDialpeer.getVendorDialpeerChildList() != null) {
                    for (VendorDialpeerChild vendorDialpeerChild : vendorDialpeer.getVendorDialpeerChildList()) {
                        if (!idsVendorDialpeerChild.contains(vendorDialpeerChild.getId()))
                            vendorDialpeerChildService.deleteVendorDialpeerChild(vendorDialpeerChild.getId());
                    }
                }
            } catch (Exception e) {
                errors.add("server_error");
            }
        }
        return errors;
    }

    @RequestMapping("/admin_full_vendor_dialpeer_delete")
    public String adminVendorDialpeerDelete(@RequestParam(value = "id_vendor_dialpeer") Long id_vendor_dialpeer) {
        vendorDialpeerService.deleteVendorDialpeer(id_vendor_dialpeer);
        return "redirect:/admin_full_vendor_dialpeer_list";
    }


    //========================= Email  account ==============================

    @RequestMapping("/admin_full_email_account_list")
    public String adminEmailAccountList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("emailAccountList", emailAccountService.getAllEmailAccountByInvisible(false));
        return "admin/admin_email_account_list";
    }

    @RequestMapping("/admin_full_email_account_invisible_list")
    public String adminEmailAccountInvisibleList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("emailAccountList", emailAccountService.getAllEmailAccountByInvisible(true));
        return "admin/admin_email_account_invisible_list";
    }

    @RequestMapping("/admin_full_email_account_edit")
    public String adminEmailAccountEdit(@RequestParam long id_email_account,
                                        Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        if (id_email_account != 0) {
            model.addAttribute("email_account", emailAccountService.getEmailAccountById(id_email_account));
        } else {
            model.addAttribute("email_account", new EmailAccount());
        }
        model.addAttribute("id_email_account", id_email_account);
        model.addAttribute("email_role_list", EmailRole.values());
        model.addAttribute("font_family", FontFamily.values());
        return "admin/admin_email_account_edit";
    }

    @RequestMapping(value = "/admin_full_email_account_edit_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> adminEmailAccountEditSave(@RequestParam long id_email_account,
                                           @RequestParam String email,
                                           @RequestParam Boolean invisible,
                                           @RequestParam String userName,
                                           @RequestParam String password,
                                           @RequestParam String smtpServer,
                                           @RequestParam String smtpPort,
                                           @RequestParam String imapServer,
                                           @RequestParam String imapPort,
                                           @RequestParam Boolean smtpSSL,
                                           @RequestParam Boolean imapSSL,
                                           @RequestParam String emailRole,
                                           @RequestParam Boolean autoAnswer,
                                           @RequestParam String textAutoAnswer,
                                           @RequestParam String textAutoEmailOutgoingTT,
                                           @RequestParam String textEmailClientRN,
                                           @RequestParam String signature,
                                           @RequestParam FontFamily outgoingFont,
                                           @RequestParam String outgoingFontSize,
                                           @RequestParam String textEmailInvoice,
                                           @RequestParam String textHtmlForPdfInvoice) {
        List<String> errors = new ArrayList<>();

        //=====Проверка данных========

        if (email == null || email.equals("") || emailAccountService.isEmail(id_email_account, email))
            errors.add("email");

        if (userName == null || userName.equals("") || emailAccountService.isUserName(id_email_account, userName))
            errors.add("userName");

        if (password == null || password.equals(""))
            errors.add("password");

        if (smtpServer == null || smtpServer.equals(""))
            errors.add("smtpServer");

        Integer smtpPortInt = null;
        try {
            smtpPortInt = Integer.parseInt(smtpPort);
        } catch (Exception e) {
            errors.add("smtpPort");
        }

        if (imapServer == null || imapServer.equals(""))
            errors.add("imapServer");

        Integer imapPortInt = null;
        try {
            imapPortInt = Integer.parseInt(imapPort);
        } catch (Exception e) {
            errors.add("imapPort");
        }

        EmailRole role = null;
        try {
            role = EmailRole.valueOf(emailRole);
            if (!role.equals(EmailRole.User) && emailAccountService.isEmailRole(id_email_account, role))
                throw new Exception();
        } catch (Exception e) {
            errors.add("emailRole");
        }

        if (signature == null || signature.equals(""))
            errors.add("signature");

        if (autoAnswer == null)
            errors.add("autoAnswer");

        if ((role.equals(EmailRole.NOC_SMS) || role.equals(EmailRole.NOC_VOIP)) && (textAutoAnswer == null || textAutoAnswer.equals("")))
            errors.add("textAutoAnswer");

        if ((role.equals(EmailRole.NOC_SMS) || role.equals(EmailRole.NOC_VOIP)) && (textAutoEmailOutgoingTT == null || textAutoEmailOutgoingTT.equals("")))
            errors.add("textAutoEmailOutgoingTT");

        Integer outgoingFontSizeInt = null;
        try {
            outgoingFontSizeInt = Integer.parseInt(outgoingFontSize);
            if (outgoingFontSizeInt == null || outgoingFontSizeInt < 1)
                throw new Exception();
        } catch (Exception e) {
            errors.add("outgoingFontSize");
        }

        //=====Сохранение данных======
        if (errors.size() == 0) {
            try {
                if (id_email_account == 0) { //новый Email Account
                    if (emailAccountService.addEmailAccount(email, userName, password, smtpServer, smtpPortInt, imapServer, imapPortInt, smtpSSL, imapSSL, role, signature, autoAnswer, textAutoAnswer, textAutoEmailOutgoingTT, textEmailClientRN, invisible, outgoingFont, outgoingFontSizeInt, textEmailInvoice, textHtmlForPdfInvoice) == null)
                        throw new Exception();
                } else {           //Изменение Email Account
                    if (emailAccountService.updateEmailAccount(id_email_account, email, userName, password, smtpServer, smtpPortInt, imapServer, imapPortInt, smtpSSL, imapSSL, role, signature, autoAnswer, textAutoAnswer, textAutoEmailOutgoingTT, textEmailClientRN, invisible, outgoingFont, outgoingFontSizeInt, textEmailInvoice, textHtmlForPdfInvoice) == null)
                        throw new Exception();
                }
            } catch (Exception e) {
                System.out.println(e);
                errors.add("server_error");
            }
        }
        return errors;
    }


    //========================= Email content ==============================

    @RequestMapping("/admin_full_email_content_list_noc_sms")                       //только для NOC_SMS
    public String adminEmailContentListNocSms(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("emailRole", EmailRole.NOC_SMS);
        return "admin/admin_email_content_list";
    }

    @RequestMapping("/admin_full_email_content_list_ratemode_sms")                       //только для Ratemode_SMS
    public String adminEmailContentListRatemodeSms(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("emailRole", EmailRole.Ratemode_SMS);
        return "admin/admin_email_content_list";
    }

    @RequestMapping(value = "/admin_full_get_email_content_page", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Map<String, Object> adminGetEmailContentPage(@RequestParam Map<String, String> filter) {
        Map<String, Object> result = new HashMap<>();
        EmailRole emailRole = EmailRole.valueOf(filter.get("emailRole"));
        List<EmailAccount> emailAccountList;
        if ((emailAccountList = emailAccountService.getAllEmailAccountByEmailRole(emailRole)).size() == 0)
            return null;
        EmailAccount emailAccount = emailAccountList.get(0);
        try {
            List<EmailContent> emailContentList = emailContentService.getEmailContentByEmailAccountForPage(emailAccount, Integer.parseInt(filter.get("pageIndex")) - 1, Integer.parseInt(filter.get("pageSize")));
            Object[] data = new Object[emailContentList.size()];
            for (int i = 0; i < emailContentList.size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("emailType", emailContentList.get(i).getEmailType().toString());
                map.put("date", emailContentList.get(i).getDate().toGMTString());
                map.put("from", emailContentList.get(i).getFrom());
                map.put("to", emailContentList.get(i).getTo());
                map.put("subject", emailContentList.get(i).getSubject());
                map.put("id", String.valueOf(emailContentList.get(i).getId()));
                StringBuilder status = new StringBuilder();
                if (emailRole.equals(EmailRole.Ratemode_SMS)) {
                    if (map.get("emailType").equals(EmailType.Outgoing.toString())) {
                        if (emailContentList.get(i).getDateSend() != null) {
                            status.append("<a href='#' style='color:green'>Sent</a>");
                        } else {
                            status.append("<a href='#' style='color:red'>Not sent</a>");
                        }
                    } else {
                        if (emailContentList.get(i).getVendorRatesUpdate() != null) {
                            status.append("<a href='/admin_full_vendor_rn_edit?id_vendor_rn=" + emailContentList.get(i).getVendorRatesUpdate().getId() + "&id_email_content=0&id_email_attachment=0'>Rate notification loaded</a>");
                        } else {
                            status.append("<a href='#' style='color:red'>Not rate notification loaded</a>");
                        }
                    }
                } else if (emailRole.equals(EmailRole.NOC_SMS)) {
                    if (map.get("emailType").equals(EmailType.Outgoing.toString())) {
                        if (emailContentList.get(i).getDateSend() != null) {
                            status.append("<a href='#' style='color:green'>Sent</a>");
                        } else {
                            status.append("<a href='#' style='color:red'>Not sent</a>");
                        }
                        if (emailContentList.get(i).getOutgoingTT() != null) {
                            status.append("<br><a href='/admin_full_outgoing_tt_edit?id_outgoing_tt=" + emailContentList.get(i).getOutgoingTT().getId() + "'>Outgoing trouble ticket</a>");
                        } else if (emailContentList.get(i).getIncomingTT() != null) {
                            status.append("<br><a href='/admin_full_incoming_tt_edit?id_incoming_tt=" + emailContentList.get(i).getIncomingTT().getId() + "'>Incoming trouble ticket</a>");
                        } else {
                            status.append("<br><a href='#' style='color:red'>No trouble ticket</a>");
                        }
                    } else {
                        if (emailContentList.get(i).getOutgoingTT() != null) {
                            status.append("<a href='/admin_full_outgoing_tt_edit?id_outgoing_tt=" + emailContentList.get(i).getOutgoingTT().getId() + "'>Outgoing trouble ticket</a>");
                        } else if (emailContentList.get(i).getIncomingTT() != null) {
                            status.append("<a href='/admin_full_incoming_tt_edit?id_incoming_tt=" + emailContentList.get(i).getIncomingTT().getId() + "'>Incoming trouble ticket</a>");
                        } else {
                            status.append("<a href='#' style='color:red'>No trouble ticket</a>");
                        }
                    }
                }
                map.put("status", status.toString());
                data[i] = map;
            }
            result.put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("itemsCount", emailAccount.getEmailContentList().size());
        return result;
    }

    @RequestMapping("/admin_full_email_content_view")
    public String adminEmailContentView(@RequestParam Long id_email_content,
                                        Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("emailContent", emailContentService.getEmailContentById(id_email_content));
        return "admin/admin_email_content_view";
    }

    @RequestMapping("/admin_full_email_content_view_ratemode_sms")
    public String adminEmailContentViewRatemodeSms(@RequestParam Long id_email_content,
                                                   Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("emailContent", emailContentService.getEmailContentById(id_email_content));
        return "admin/admin_email_content_view_ratemode_sms";
    }

    @RequestMapping(value = "/admin_full_get_email_attachment", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Resource> getEmailAttachment(@RequestParam Long id_email_attachment) {
        try {
            Resource rf = new ByteArrayResource(emailAttachmentService.getEmailAttachmentById(id_email_attachment).getFileBody());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + coderService.encoderUtf8ToRfc5987(emailAttachmentService.getEmailAttachmentById(id_email_attachment).getFileName())).body(rf);  //переделать
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //========================= Date format  =================================

    @RequestMapping("/admin_full_date_format_list")
    public String adminDateFormatList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        model.addAttribute("name", customUser.getName());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("title", settingsService.getSiteTitle());
        model.addAttribute("dateFormatList", dateFormatService.getAllDateFormat());
        return "admin/admin_date_format_list";
    }

    @RequestMapping(value = "/admin_full_date_format_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Long adminDateFormatSave(@RequestParam Long id,
                             @RequestParam String format) {
        return dateFormatService.saveDateFormat(id, format);
    }

    @RequestMapping(value = "/admin_full_date_format_delete", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    void adminDateFormatDelete(@RequestParam Long id) {
        dateFormatService.deleteDateFormat(id);
    }


    //========================= Rest Controllers  ==============================

    @RequestMapping(value = "/admin_full_get_rates_history_rows", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Refbook> getRatesHistoryRows(@RequestParam Long account_id,
                                      @RequestParam String mode,
                                      @RequestParam String mcc) {
        List<Refbook> returnList = clientRatesHistoryService.getOpenedRefbooksByAccountAndMcc(account_id, mcc, mode);

        return returnList;
    }

    @RequestMapping(value = "/admin_full_get_opened_mcc_by_account", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Refbook> getMessagingFlowReport(@RequestParam Long account_id,
                                         @RequestParam String mcc,
                                         @RequestParam String mode) {
        List<Refbook> returnList = clientRatesHistoryService.getOpenedRefbooksByAccountAndMcc(account_id, mcc, mode);

        return returnList;
    }

    @RequestMapping(value = "/admin_full_messaging_flow_list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<MessagingFlowRow> getMessagingFlowReport() {
        return mdrXmlListService.getMessagingFlowList();
    }

    @RequestMapping(value = "/admin_full_index_report", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    IndexManagerReport getIndexData() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        IndexManagerReport returnObject = indexManagerReportService.getIndexManagerReport(login);

        return returnObject;
    }

    @RequestMapping(value = "/admin_full_report_soa", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    SoaReport getSoaJSON(@RequestParam String date_start_string,
                         @RequestParam String date_end_string,
                         @RequestParam Long id_customer) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        SoaReport report = new SoaReport();
        try {
            Date start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_start_string);
            Date end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_end_string);
            report = soaService.getSoaReportByPreiodAndCustomer(start_date, end_date, id_customer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return report;
    }

    @RequestMapping(value = "/admin_full_report_balances", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<BalancesRow> getBalancesJSON(@RequestParam Long id_customer) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        List<BalancesRow> returnList = new ArrayList<>();
        List<Long> customerIdsList = new ArrayList<>();

        if (id_customer != 0) {
            customerIdsList.add(id_customer);
        } else {
            List<Customer> customerList = customerService.getCustomerAllSort();

            for (Customer currentCustomer : customerList) {
                customerIdsList.add(currentCustomer.getId());
            }
        }

        Map<Long, Double> balancesMap = soaService.getBalanceOnDate(new Date(0), customerIdsList);

        Iterator it = balancesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Long id = (Long) pair.getKey();
            Double balance = (Double) pair.getValue();

            returnList.add(new BalancesRow(customerService.getCustomerById(id).getName(), balance));
            it.remove();
        }


        return returnList;
    }

    @RequestMapping(value = "/admin_full_report_sms_statistic", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ReportSmsStatistic getReportSmsStatisticInJSON(@RequestParam Long id_customer,
                                                   @RequestParam String mcc,
                                                   @RequestParam String date_start,
                                                   @RequestParam String date_end,
                                                   @RequestParam String report_option) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        ReportSmsStatistic reportSmsStatistic = reportSmsStatisticService.getReportSmsStatistic(customUser, id_customer, mcc, date_start, date_end, report_option);
        return reportSmsStatistic;
    }


    @RequestMapping(value = "/admin_full_report_mdr", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    MdrXmlList getReportMdr(@RequestParam Map<String, Object> all) {
        MdrXmlList mdrXmlList = new MdrXmlList();
        try {
            mdrXmlList = mdrXmlListService.getMdrXmlListByParam(Arrays.<String>asList(all.get("msgid_list_filter").toString().split(",")),
                    Arrays.<String>asList(all.get("vendor_msgid_list_filter").toString().split(",")),
                    all.get("created_at_start_filter").toString(),
                    all.get("created_at_end_filter").toString(),
                    all.get("smpp_client_account_id_filter").toString(),
                    all.get("smpp_vendor_account_id_filter").toString(),
                    Arrays.<String>asList(all.get("destination_addr_list_filter").toString().split(",")),
                    Arrays.<String>asList(all.get("source_addr_list_filter").toString().split(",")),
                    all.get("mcc_filter").toString(),
                    Arrays.<String>asList(all.get("mnc_list_filter").toString().split(",")),
                    Long.parseLong(all.get("first_number").toString()),
                    Long.parseLong(all.get("last_number").toString()),
                    Integer.parseInt(all.get("amount_of_rows").toString()),
                    all.get("direction").toString(),
                    all.get("sort_list").toString(),
                    Long.valueOf(all.get("min_id").toString()),
                    Long.valueOf(all.get("max_id").toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mdrXmlList;
    }


    @RequestMapping(value = "/admin_full_get_mnc_by_mcc", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    MncXmlList getMncByMccInJSON(@RequestParam String mcc) {
        return mncListService.getMncXmlListByMcc(mcc);
    }

    @RequestMapping(value = "/admin_full_get_mcc", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    HashMap<String, String> getCountryAndMcc() {
        HashMap<String, String> countryMap = new HashMap<>();
        for (Refbook r : refbookService.getRefbookAllCountrySort()) {
            countryMap.put(r.getCountry(), r.getMcc());
        }
        return countryMap;
    }

    @RequestMapping(value = "/admin_full_get_mnc", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    HashMap<String, String> getNetworkAndMnc() {
        HashMap<String, String> networkMap = new HashMap<>();
        for (Refbook r : refbookService.getRefbookAllNetworkSort()) {
            networkMap.put(r.getNetwork(), r.getMcc() + " " + r.getMnc());
        }
        return networkMap;
    }

    @RequestMapping(value = "/admin_full_get_sms_routing_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    RoutingSmsRuleXmlList getSmsRoutingList(@RequestParam Long client_id,
                                            @RequestParam String mcc,
                                            @RequestParam SmsRoutingLevel sms_routing_level) {
        RoutingSmsRuleXmlList routingSmsRuleXmlList = new RoutingSmsRuleXmlList();
        List<RoutingSmsRule> routingSmsRules = routingSmsRuleService.getAllByLevelMccAndAccount(sms_routing_level, client_id, mcc);
        routingSmsRuleXmlList.setRoutingSmsRuleList(routingSmsRules);
        System.out.println(client_id + " " + mcc + " " + sms_routing_level + " size:" + routingSmsRules.size());
        return routingSmsRuleXmlList;
    }

    @RequestMapping(value = "/admin_full_save_sms_routing_item", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Response saveSmsRoutingItem(@RequestParam String id,
                                @RequestParam SmsRoutingLevel level,
                                @RequestParam Long account,
                                @RequestParam String country,
                                @RequestParam String network,
                                @RequestParam String vendor,
                                @RequestParam String sourceFilter,
                                @RequestParam String messageFilter,
                                @RequestParam String vendorFilter,
                                @RequestParam Integer ruleOrder,
                                @RequestParam Integer weight,
                                @RequestParam Boolean filtered,
                                @RequestParam Boolean replaceSource,
                                @RequestParam Boolean replaceRegisteredDelivery,
                                @RequestParam Boolean registeredDelivery,
                                @RequestParam Boolean filteredContent) {


        Long vendorId = Long.valueOf(vendor.replace("dp_", ""));
        Long vendorFilterId = Long.valueOf(vendorFilter.replace("dp_", ""));
        VendorTypes vendorType = VendorTypes.SmppClientAccount;
        VendorTypes vendorFilterType = VendorTypes.SmppClientAccount;

        if (vendor.contains("dp_")) {
            vendorType = VendorTypes.VendorDialpeer;
        }

        if (vendorFilter.contains("dp_")) {
            vendorFilterType = VendorTypes.VendorDialpeer;
        }

        String routing_id = routingSmsRuleService.SaveRoutingRule(id, level, account, country, network, vendorId, sourceFilter, messageFilter, vendorFilterId, ruleOrder, weight, filtered, replaceSource, replaceRegisteredDelivery, registeredDelivery, filteredContent, vendorType, vendorFilterType);
        Response response = new Response();
        response.setStatus("OK");
        response.setParameter(routing_id);
        return response;
    }

    @RequestMapping(value = "/admin_full_delete_sms_routing_item", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Response saveSmsRoutingItem(@RequestParam String id) {
        routingSmsRuleService.deleteRoutingRule(id);
        Response response = new Response();
        response.setStatus("OK");
        response.setParameter(id);
        return response;
    }

    @RequestMapping(value = "/admin_full_get_accounts_by_client_vendor", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<LongText> getClientVendorByMode(@RequestParam String mode) {
        List<LongText> returnList = new ArrayList<>();
        if (mode.equals("client")) {
            List<SmppClientAccount> list = smppClientAccountService.getSmppClientAccountAllSort();
            for (SmppClientAccount account : list) {
                returnList.add(new LongText(account.getId(), account.getCustomer().getName() + " - " + account.getName()));
            }
        } else {
            List<SmppVendorAccount> list = smppVendorAccountService.getSmppVendorAccountAllSort();
            for (SmppVendorAccount account : list) {
                returnList.add(new LongText(account.getId(), account.getCustomer().getName() + " - " + account.getName()));
            }
        }
        return returnList;
    }

    @RequestMapping(value = "/admin_full_get_rates_history_report", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<RatesHistoryReportRow> getRatesHistoryReport(@RequestParam String mode,
                                                      @RequestParam Long account,
                                                      @RequestParam String country) {


        return ratesReportsService.getRatesHistoryReport(mode, account, country);
    }


    @RequestMapping(value = "/admin_full_get_actual_rate_report", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<ActualRatesReportRow> getActualRatesReport(@RequestParam String mode,
                                                    @RequestParam Long account,
                                                    @RequestParam String country) {
        return ratesReportsService.getActualRatesReport(mode, account, country);
    }

    @RequestMapping(value = "/admin_full_save_test_template", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Response saveTestTemplate(@RequestParam String sourceAddr,
                              @RequestParam String text) {
        Response response = new Response();

        List<TestText> checkList = testTextsRepository.getTemplateByAddrAndText(sourceAddr, text);
        if (checkList.size() > 0) {
            response.setStatus("Error");
            response.setParameter("Same template already exists.");
        } else {
            try {
                TestText newTemplate = new TestText();
                newTemplate.setText(text);
                newTemplate.setSourceAddr(sourceAddr);
                testTextsRepository.save(newTemplate);
                response.setStatus("Ok");
                response.setParameter("Saved");
            } catch (Exception e) {
                response.setStatus("Error");
                response.setParameter("Error");
            }
        }
        return response;
    }

    @RequestMapping(value = "/admin_full_save_basic_test", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Response saveBasicTest(@RequestParam String sourceaddr,
                           @RequestParam String templatetext,
                           @RequestParam String country,
                           @RequestParam String network,
                           @RequestParam String vendors) {
        Response response = new Response();

        String mnc = "";
        if (network.equals("0")) {
            mnc = "Flt";
        } else {
            mnc = network;
        }

        try {
            BasicTest newBasicTest = new BasicTest();
            newBasicTest.setText(templatetext);
            newBasicTest.setSourceAddr(sourceaddr);
            newBasicTest.setDate(new Date());
            newBasicTest.setMcc(country);
            newBasicTest.setMnc(mnc);
            basicTestService.save(newBasicTest);

            List<String> vendorsIdList = Arrays.asList(vendors.split(","));
            for (String vendorId : vendorsIdList) {
                BasicTestDetails currentDetail = new BasicTestDetails();
                currentDetail.setText(templatetext);
                currentDetail.setSourceAddr(sourceaddr);
                currentDetail.setVendorAccount(Long.valueOf(vendorId));
                currentDetail.setTestId(newBasicTest.getId());
                currentDetail.setMcc(country);
                currentDetail.setMnc(mnc);
                basicTestDetailService.save(currentDetail);
            }

            response.setStatus("Ok");
            response.setParameter("Ok");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("Error");
            response.setParameter("Error");
        }

        return response;
    }

    @RequestMapping(value = "/admin_full_get_basic_test_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<BasicTest> getBasicTestList(@RequestParam Integer page) {
        return basicTestService.getBasicTestListByBage(page);
    }

    @RequestMapping(value = "/admin_full_get_basic_test_details_list", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<BasicTestReportRow> getBasicTestDetailsList(@RequestParam Long testId) {

        List<BasicTestDetails> detailsList = basicTestDetailService.getDetailsByTestId(testId);
        List<BasicTestReportRow> reports = new ArrayList<>();

        String lastkey = "";

        BasicTestReportRow newReport = null;

        for (BasicTestDetails currentElement : detailsList) {
            if (currentElement.getId() + currentElement.getMcc() + currentElement.getMnc() != lastkey) {
                if (newReport != null) {
                    reports.add(newReport);
                }

                newReport = new BasicTestReportRow();
                String countryName = "";
                String networkName = "";

                if (currentElement.getDestinationAddr() != null) {
                    Refbook currentNetworkRef = refbookService.getRefbookByNumber(currentElement.getDestinationAddr());
                    if (currentNetworkRef != null) {
                        List<Refbook> currentNetworkRefList = refbookService.getRefbookByMccAndMnc(currentNetworkRef.getMcc(), currentNetworkRef.getMnc());
                        if (currentNetworkRefList.size() > 0) {
                            networkName = currentNetworkRefList.get(0).getNetwork();
                        }

                        Refbook currentCountryRef = refbookService.getRootRefbookByMcc(currentNetworkRef.getMcc());
                        if (currentCountryRef != null) {
                            countryName = currentCountryRef.getCountry();
                        }
                    }
                }

                SmppVendorAccount smppVendorAccount = smppVendorAccountService.getSmppVendorAccountById(currentElement.getVendorAccount());
                newReport.setVendorName(smppVendorAccount.getCustomer().getName() + " - " + smppVendorAccount.getName());
                newReport.setCountryName(countryName);
                newReport.setNetworkName(networkName);

                newReport.addToDetailsList(currentElement);

            } else {

            }
            lastkey = currentElement.getId() + currentElement.getMcc() + currentElement.getMnc();
        }

        if (newReport != null) {
            reports.add(newReport);
        }

        return reports;
    }


    @RequestMapping(value = "/admin_full_customer_smpp_vendor_account_list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    CustomerSmppVendorAccountList getAdminCustomerSmppVendorSystemId() {
        CustomerSmppVendorAccountList customerSmppVendorAccountList = customerSmppVendorAccountListService.getFullAccountList();
        return customerSmppVendorAccountList;
    }
}