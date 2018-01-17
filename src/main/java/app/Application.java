package app;

import app.entity.*;
import app.entity.enums.*;
import app.service.customer.CustomerService;
import app.service.customerStatus.CustomerStatusService;
import app.service.dateFormat.DateFormatService;
import app.service.emailAccount.EmailAccountService;
import app.service.mdr.MdrService;
import app.service.refbook.RefbookService;
import app.service.routingSmsRule.RoutingSmsRuleService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppClientIps.SmppClientIpsService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.smppVendorIps.SmppVendorIpsService;
import app.service.smsBillingTerms.SmsBillingTermsService;
import app.service.totalsSms.TotalsSmsService;
import app.service.user.UserService;
import app.service.voiceBillingTerms.VoiceBillingTermsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.*;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner demo(final UserService userService,
                                  final CustomerStatusService customerStatusService,
                                  final CustomerService customerService,
                                  final SmsBillingTermsService smsBillingTermsService,
                                  final VoiceBillingTermsService voiceBillingTermsService,
                                  final SmppVendorAccountService smppVendorAccountService,
                                  final SmppVendorIpsService smppVendorIpsService,
                                  final SmppClientAccountService smppClientAccountService,
                                  final SmppClientIpsService smppClientIpsService,
                                  final SmppClientSystemIdService smppClientSystemIdService,
                                  final RefbookService refbookService,
                                  final MdrService mdrService,
                                  final EmailAccountService emailAccountService,
                                  final TotalsSmsService totalsSmsService,
                                  final RoutingSmsRuleService routingSmsRuleService,
                                  final DateFormatService dateFormatService) {
        return new CommandLineRunner() {

            @Override
            public void run(String... strings) throws Exception {


                if (dateFormatService.getAllDateFormat().size() == 0) {
                    dateFormatService.addDateFormat(new DateFormat("dd-MM-yyyy HH:mm:ss"));
                    dateFormatService.addDateFormat(new DateFormat("dd-MM-yyyy HH:mm"));
                    dateFormatService.addDateFormat(new DateFormat("dd.MM.yyyy HH:mm:ss"));
                    dateFormatService.addDateFormat(new DateFormat("dd.MM.yyyy HH:mm"));
                    dateFormatService.addDateFormat(new DateFormat("yyyy-MM-dd HH:mm:ss"));
                    dateFormatService.addDateFormat(new DateFormat("yyyy-MM-dd HH:mm"));
                    dateFormatService.addDateFormat(new DateFormat("yyyy.MM.dd HH:mm:ss"));
                    dateFormatService.addDateFormat(new DateFormat("yyyy.MM.dd HH:mm"));
                }

                if (userService.getUsersAll().size() == 0) {
                    userService.addUser(new CustomUser("user1", "admin", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "phone", "email", UserRole.Administrator));
                }


//                    totalsSmsService.save(new TotalsSmsRow("6354", "57354", Timestamp.from(Instant.now()), "255", "6", 0.214, 0.054, 654l, 987l, 874l));
//                    totalsSmsService.save(new TotalsSmsRow("6354", "57354", Timestamp.from(Instant.now()), "255", "1", 0.214, 0.054, 654l, 987l, 874l));
//                    totalsSmsService.save(new TotalsSmsRow("6354", "57354", Timestamp.from(Instant.now()), "250", "1", 0.214, 0.054, 654l, 987l, 874l));
//                    totalsSmsService.save(new TotalsSmsRow("6354", "57354", Timestamp.from(Instant.now()), "250", "1", 0.214, 0.054, 654l, 987l, 874l));
//                    totalsSmsService.save(new TotalsSmsRow("6354", "57354", Timestamp.from(Instant.now()), "250", "1", 0.214, 0.054, 654l, 987l, 874l));

//                    mdrService.save(new Mdr("14c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid211","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid111",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 16, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("24c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid212","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid112",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 17, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("34c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid221","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid121",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 18, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("44c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid222","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid122",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 19, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("54c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid222","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid122",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 20, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("64c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid111","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid211",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 21, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("74c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid112","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid212",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 22, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("84c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid121","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid221",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 23, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("94c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid122","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid222",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 24, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));
//                    mdrService.save(new Mdr("10c761b3-8f34-4976-8ba1-81664800e26b","smppsapi","cid122","888304","523353311794",1,"Francisco Javier esta llegando ahora en un Ford Fiesta (JLW4606).",null,"uid222",1, Timestamp.from(Instant.now()), new Timestamp(2017, 10, 10, 18, 29, 25, 0),true,"510","8","212481",0.0018f,0.0019f,"ESME_ROK","UNDELIV",15000l));

//                if (customerService.getCustomerAll().size() == 0) {
//
//                    customerStatusService.addCustomerStatus("Potential Voice", false);
//                    customerStatusService.addCustomerStatus("Potential SMS", false);
//                    customerStatusService.addCustomerStatus("Working SMS", false);
//                    customerStatusService.addCustomerStatus("Working Voice", false);
//                    customerStatusService.addCustomerStatus("Invisible", false);
//                    smsBillingTermsService.addSmsBillingTerms("1/1", 1, 1);
//                    smsBillingTermsService.addSmsBillingTerms("7/3", 7, 3);
//                    smsBillingTermsService.addSmsBillingTerms("7/7", 7, 7);
//                    smsBillingTermsService.addSmsBillingTerms("7/1", 7, 1);
//                    smsBillingTermsService.addSmsBillingTerms("7/5", 7, 5);
//                    smsBillingTermsService.addSmsBillingTerms("15/1", 15, 1);
//                    smsBillingTermsService.addSmsBillingTerms("15/3", 15, 3);
//                    smsBillingTermsService.addSmsBillingTerms("15/5", 15, 5);
//                    smsBillingTermsService.addSmsBillingTerms("15/7", 15, 7);
//                    smsBillingTermsService.addSmsBillingTerms("15/15", 15, 15);
//                    smsBillingTermsService.addSmsBillingTerms("30/3", 30, 3);
//                    smsBillingTermsService.addSmsBillingTerms("30/7", 30, 7);
//                    smsBillingTermsService.addSmsBillingTerms("30/15", 30, 15);
//                    voiceBillingTermsService.addVoiceBillingTerms("v1/1", 1, 1);
//                    voiceBillingTermsService.addVoiceBillingTerms("v7/3", 7, 3);
//                    voiceBillingTermsService.addVoiceBillingTerms("v7/7", 7, 7);
//                    voiceBillingTermsService.addVoiceBillingTerms("v7/1", 7, 1);
//                    voiceBillingTermsService.addVoiceBillingTerms("v7/5", 7, 5);
//                    voiceBillingTermsService.addVoiceBillingTerms("v15/1", 15, 1);
//                    voiceBillingTermsService.addVoiceBillingTerms("v15/3", 15, 3);
//                    voiceBillingTermsService.addVoiceBillingTerms("v15/5", 15, 5);
//                    voiceBillingTermsService.addVoiceBillingTerms("v15/7", 15, 7);
//                    voiceBillingTermsService.addVoiceBillingTerms("v15/15", 15, 15);
//                    voiceBillingTermsService.addVoiceBillingTerms("v30/3", 30, 3);
//                    voiceBillingTermsService.addVoiceBillingTerms("v30/7", 30, 7);
//                    voiceBillingTermsService.addVoiceBillingTerms("v30/15", 30, 15);
//
//                    customerService.addCustomer(new Customer(
//                            "Customer1",
//                            "123456789",
//                            "artem.khirgii@gmail.com",
//                            "web.site.com",
//                            userService.getUserByLogin("admin"),
//                            customerStatusService.getCustomerStatusByName("Potential SMS")
//                    ));
//                    smppVendorAccountService.addSmppVendorAccount("VA11", true, customerService.getCustomerByName("Customer1"), null, false, 10, 86400, DataCoding.SMSC_Default, 30, 30, true, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.National, 120, 0, 0, true, 10, 120, 300, false, BindType.BIND_TRX, false);
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("192.168.1.1.127", 8080, "vsi111", "password", "st", 0l, "cid111", true, smppVendorAccountService.getSmppVendorAccountByName("VA11"), 0l));
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("192.168.1.1.128", 8080, "vsi112", "password", "st", 0l, "cid112", true, smppVendorAccountService.getSmppVendorAccountByName("VA11"), 0l));
//                    smppVendorAccountService.addSmppVendorAccount("VA12", true, customerService.getCustomerByName("Customer1"), null, false, 10, 86400, DataCoding.SMSC_Default, 30, 30, true, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.National, 120, 0, 0, true, 10, 120, 300, false, BindType.BIND_TRX, false);
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("192.168.1.1.129", 8080, "vsi121", "password", "st", 0l, "cid121", true, smppVendorAccountService.getSmppVendorAccountByName("VA12"), 0l));
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("192.168.1.1.130", 8080, "vsi122", "password", "st", 0l, "cid122", true, smppVendorAccountService.getSmppVendorAccountByName("VA12"), 0l));
//                    smppClientAccountService.addSmppClientAccount("CA11", "st", 0l, customerService.getCustomerByName("Customer1"), false);
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("192.168.1.1.135", true, smppClientAccountService.getSmppClientAccountByName("CA11")));
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("192.168.1.1.136", true, smppClientAccountService.getSmppClientAccountByName("CA11")));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI111", "password", "uid111", smppClientAccountService.getSmppClientAccountByName("CA11"));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI112", "password", "uid112", smppClientAccountService.getSmppClientAccountByName("CA11"));
//                    smppClientAccountService.addSmppClientAccount("CA12", "st", 0l, customerService.getCustomerByName("Customer1"), false);
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("192.168.1.1.137", true, smppClientAccountService.getSmppClientAccountByName("CA12")));
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("192.168.1.1.138", true, smppClientAccountService.getSmppClientAccountByName("CA12")));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI121", "password", "uid121", smppClientAccountService.getSmppClientAccountByName("CA12"));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI122", "password", "uid122", smppClientAccountService.getSmppClientAccountByName("CA12"));
//
//                    customerService.addCustomer(new Customer(
//                            "Customer2",
//                            "223456789",
//                            "cmua76@outlook.com",
//                            "web2.site.com",
//                            userService.getUserByLogin("admin"),
//                            customerStatusService.getCustomerStatusByName("Potential SMS")
//                    ));
//                    smppVendorAccountService.addSmppVendorAccount("VA21", true, customerService.getCustomerByName("Customer2"), null, false, 10, 86400, DataCoding.SMSC_Default, 30, 30, true, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.National, 120, 0, 0, true, 10, 120, 300, false, BindType.BIND_TRX, false);
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("2.168.1.1.127", 8080, "vsi211", "password", "st", 0l, "cid211", true, smppVendorAccountService.getSmppVendorAccountByName("VA21"), 0l));
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("2.168.1.1.128", 8080, "vsi212", "password", "st", 0l, "cid212", true, smppVendorAccountService.getSmppVendorAccountByName("VA21"), 0l));
//                    smppVendorAccountService.addSmppVendorAccount("VA22", true, customerService.getCustomerByName("Customer2"), null, false, 10, 86400, DataCoding.SMSC_Default, 30, 30, true, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.National, 120, 0, 0, true, 10, 120, 300, false, BindType.BIND_TRX, false);
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("2.168.1.1.129", 8080, "vsi221", "password", "st", 0l, "cid221", true, smppVendorAccountService.getSmppVendorAccountByName("VA22"), 0l));
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("2.168.1.1.130", 8080, "vsi222", "password", "st", 0l, "cid222", true, smppVendorAccountService.getSmppVendorAccountByName("VA22"), 0l));
//                    smppClientAccountService.addSmppClientAccount("CA21", "st", 0l, customerService.getCustomerByName("Customer2"), false);
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("2.168.1.1.135", true, smppClientAccountService.getSmppClientAccountByName("CA21")));
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("2.168.1.1.136", true, smppClientAccountService.getSmppClientAccountByName("CA21")));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI211", "password", "uid211", smppClientAccountService.getSmppClientAccountByName("CA21"));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI212", "password", "uid212", smppClientAccountService.getSmppClientAccountByName("CA21"));
//                    smppClientAccountService.addSmppClientAccount("CA22", "st", 0l, customerService.getCustomerByName("Customer2"), false);
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("2.168.1.1.137", true, smppClientAccountService.getSmppClientAccountByName("CA22")));
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("2.168.1.1.138", true, smppClientAccountService.getSmppClientAccountByName("CA22")));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI221", "password", "uid221", smppClientAccountService.getSmppClientAccountByName("CA22"));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI222", "password", "uid222", smppClientAccountService.getSmppClientAccountByName("CA22"));
//
//                    customerService.addCustomer(new Customer(
//                            "Customer3",
//                            "323456789",
//                            "chtoto3@tam.com",
//                            "web3.site.com",
//                            userService.getUserByLogin("admin"),
//                            customerStatusService.getCustomerStatusByName("Potential SMS")
//                    ));
//                    smppVendorAccountService.addSmppVendorAccount("VA31", true, customerService.getCustomerByName("Customer3"), null, false, 10, 86400, DataCoding.SMSC_Default, 30, 30, true, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.National, 120, 0, 0, true, 10, 120, 300, false, BindType.BIND_TRX, false);
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("3.168.1.1.127", 8080, "vsi311", "password", "st", 0l, "cid311", true, smppVendorAccountService.getSmppVendorAccountByName("VA31"), 0l));
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("3.168.1.1.128", 8080, "vsi312", "password", "st", 0l, "cid312", true, smppVendorAccountService.getSmppVendorAccountByName("VA31"), 0l));
//                    smppVendorAccountService.addSmppVendorAccount("VA32", true, customerService.getCustomerByName("Customer3"), null, false, 10, 86400, DataCoding.SMSC_Default, 30, 30, true, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.Unknown, NPI.ISDN, TON.National, 120, 0, 0, true, 10, 120, 300, false, BindType.BIND_TRX, false);
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("3.168.1.1.129", 8080, "vsi321", "password", "st", 0l, "cid321", true, smppVendorAccountService.getSmppVendorAccountByName("VA32"), 0l));
//                    smppVendorIpsService.addSmppVendorIps(new SmppVendorIps("3.168.1.1.130", 8080, "vsi322", "password", "st", 0l, "cid322", true, smppVendorAccountService.getSmppVendorAccountByName("VA32"), 0l));
//                    smppClientAccountService.addSmppClientAccount("CA31", "st", 0l, customerService.getCustomerByName("Customer3"), false);
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("3.168.1.1.135", true, smppClientAccountService.getSmppClientAccountByName("CA31")));
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("3.168.1.1.136", true, smppClientAccountService.getSmppClientAccountByName("CA31")));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI311", "password", "uid311", smppClientAccountService.getSmppClientAccountByName("CA31"));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI312", "password", "uid312", smppClientAccountService.getSmppClientAccountByName("CA31"));
//                    smppClientAccountService.addSmppClientAccount("CA32", "st", 0l, customerService.getCustomerByName("Customer3"), false);
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("3.168.1.1.137", true, smppClientAccountService.getSmppClientAccountByName("CA32")));
//                    smppClientIpsService.addSmppClientIps(new SmppClientIps("3.168.1.1.138", true, smppClientAccountService.getSmppClientAccountByName("CA32")));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI321", "password", "uid321", smppClientAccountService.getSmppClientAccountByName("CA32"));
//                    smppClientSystemIdService.addSmppCleintSystemId("CSI322", "password", "uid322", smppClientAccountService.getSmppClientAccountByName("CA32"));
//
//                }
//
                if (emailAccountService.getAllEmailAccountByInvisible(true).size() == 0 && emailAccountService.getAllEmailAccountByInvisible(false).size() == 0){
                    emailAccountService.addEmailAccount("proba.billing@gmail.com", "proba.billing", "volumsproba", "smtp.gmail.com", 587, "imap.gmail.com", 993, true, true, EmailRole.NOC_SMS, "<p>Sigmature</p>", true, "<p>Text auto answer</p>", "<p>Text auto email OutgoingTT<br>[MessagesBlock]</p>", "<p>Text email client RN</p>", false, FontFamily.Times_New_Roman, 25, "", "");
                    emailAccountService.addEmailAccount("proba2.billing@gmail.com", "proba2.billing", "volumsproba", "smtp.gmail.com", 587, "imap.gmail.com", 993, true, true, EmailRole.Ratemode_SMS, "<p>Sigmature proba2</p>", true, "<p>Text auto answer proba2</p>", "<p>Text auto email OutgoingTT proba2<br>[MessagesBlock]</p>", "<p>Text email client RN</p>", false, FontFamily.Times_New_Roman, 25, "", "");
                }
//
//                if (refbookService.getRefbookAll().size() == 0) {
//                    //fillRefbook();
//                }
            }

        };
    }
}