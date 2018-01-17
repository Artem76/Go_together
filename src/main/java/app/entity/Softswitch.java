package app.entity;

import app.entity.enums.SoftswitchType;
import app.service.softswitchTriggers.SoftswitchTriggersService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Олег on 06.07.2017.
 */

@Entity
@Table(name = "softswitches")
public class Softswitch {

    @Transient
    @Autowired
    private SoftswitchTriggersService softswitchTriggersService;

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "softswitch_type")
    private SoftswitchType softswitch_type;

    @Column(name = "host")
    private String host;

    @Column(name = "mysql_port")
    private Integer mysql_port;

    @Column(name = "mysql_database")
    private String mysql_database;

    @Column(name = "mysql_username")
    private String mysql_username;

    @Column(name = "mysql_password")
    private String mysql_password;

    @Column(name = "mysql_dr_table")
    private String mysql_dr_table;

    @Column(name = "mysql_totals_table")
    private String mysql_totals_table;

    @Column(name = "mysql_stats_table")
    private String mysql_stats_table;

    @Column(name = "mysql_reports_table")
    private String mysql_reports_table;

    @Column(name = "api_port")
    private Integer api_port;

    @Column(name = "api_username")
    private String api_username;

    @Column(name = "api_password")
    private String api_password;

    @Column(name = "last_loaded_dr")
    private long last_loaded_dr = 82066731L;

    @Column(name = "last_loaded_totals")
    private long last_loaded_totals = 0;

    @Column(name = "last_loaded_report")
    private long last_loaded_report = 0;


    public Softswitch(String name, SoftswitchType softswitch_type, String host, Integer mysql_port, String mysql_database, String mysql_username,
                      String mysql_password, String mysql_dr_table, String mysql_totals_table, String mysql_stats_table, String mysql_reports_table,
                      Integer api_port, String api_username, String api_password) {
        this.name = name;
        this.softswitch_type = softswitch_type;
        this.host = host;
        this.mysql_port = mysql_port;
        this.mysql_database = mysql_database;
        this.mysql_username = mysql_username;
        this.mysql_password = mysql_password;
        this.mysql_dr_table = mysql_dr_table;
        this.mysql_totals_table = mysql_totals_table;
        this.mysql_stats_table = mysql_stats_table;
        this.mysql_reports_table = mysql_reports_table;
        this.api_port = api_port;
        this.api_username = api_username;
        this.api_password = api_password;
    }

    public Softswitch(String name, SoftswitchType softswitch_type, String host, Integer mysql_port, String mysql_database, String mysql_username,
                      String mysql_password, String mysql_dr_table, String mysql_totals_table, String mysql_stats_table, String mysql_reports_table,
                      Integer api_port, long last_loaded_dr, long last_loaded_report) {
        this.name = name;
        this.softswitch_type = softswitch_type;
        this.host = host;
        this.mysql_port = mysql_port;
        this.mysql_database = mysql_database;
        this.mysql_username = mysql_username;
        this.mysql_password = mysql_password;
        this.mysql_dr_table = mysql_dr_table;
        this.mysql_totals_table = mysql_totals_table;
        this.mysql_stats_table = mysql_stats_table;
        this.mysql_reports_table = mysql_reports_table;
        this.api_port = api_port;
        this.last_loaded_dr = last_loaded_dr;
        this.last_loaded_report = last_loaded_report;
    }

    public Softswitch() {

    }

    public long getLast_loaded_totals() {
        return last_loaded_totals;
    }

    public void setLast_loaded_totals(long last_loaded_totals) {
        this.last_loaded_totals = last_loaded_totals;
    }

    public long getLast_loaded_report() {
        return last_loaded_report;
    }

    public void setLast_loaded_report(long last_loaded_report) {
        this.last_loaded_report = last_loaded_report;
    }

    public long getLastLoadedDr() {
        return this.last_loaded_dr;
    }

    public long getId() {
        return this.id;
    }


    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public SoftswitchType getType() {
        return softswitch_type;
    }

    public String getTypeString() {
        return softswitch_type.toString();
    }

    public Integer getMysqlPort() {
        return mysql_port;
    }

    public String getMysqlUsername(){
        return mysql_username;
    }

    public String getMysqlPassword (){
        return mysql_password;
    }

    public String getMysqlDrTable(){
        return mysql_dr_table;
    }

    public String getMysqlTotalsTable(){
        return mysql_totals_table;
    }

    public String getMysqlStatsTable(){
        return mysql_stats_table;
    }

    public String getMysqlReportsTable(){
        return mysql_reports_table;
    }

    public String getMysqlDatabase() {
        return mysql_database;
    }

    public Integer getApiPort() {
        return api_port;
    }

    public void setLastLoadedDr(long last_loaded_dr) {
        this.last_loaded_dr = last_loaded_dr;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSoftswitchType(SoftswitchType type) {
        this.softswitch_type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setMysqlPort(Integer mysql_port) {
        this.mysql_port = mysql_port;
    }

    public void setMysqlDatabase(String mysql_database) {
        this.mysql_database = mysql_database;
    }

    public void setMysqlUsername(String mysql_username) {
        this.mysql_username = mysql_username;
    }

    public void setMysqlPassword(String mysql_password) {
        this.mysql_password = mysql_password;
    }

    public void setMysqlDrTable(String mysql_dr_table) {
        this.mysql_dr_table = mysql_dr_table;
    }

    public void setMysqlTotalsTable(String mysql_totals_table) {
        this.mysql_totals_table = mysql_totals_table;
    }

    public void setMysqlStatsTable(String mysql_stats_table) {
        this.mysql_stats_table = mysql_stats_table;
    }

    public void setMysqlReportsTable(String mysql_reports_table) {
        this.mysql_reports_table = mysql_reports_table;
    }

    public void setApiPort(Integer api_port) {
        this.api_port = api_port;
    }

    public String getApi_username() {
        return api_username;
    }

    public void setApi_username(String api_username) {
        this.api_username = api_username;
    }

    public String getApi_password() {
        return api_password;
    }

    public void setApi_password(String api_password) {
        this.api_password = api_password;
    }




    public SoftswitchTriggersService getSoftswitchTriggersService() {
        return softswitchTriggersService;
    }

    public void setSoftswitchTriggersService(SoftswitchTriggersService softswitchTriggersService) {
        this.softswitchTriggersService = softswitchTriggersService;
    }

    public SoftswitchType getSoftswitch_type() {
        return softswitch_type;
    }

    public void setSoftswitch_type(SoftswitchType softswitch_type) {
        this.softswitch_type = softswitch_type;
    }

    public Integer getMysql_port() {
        return mysql_port;
    }

    public void setMysql_port(Integer mysql_port) {
        this.mysql_port = mysql_port;
    }

    public String getMysql_database() {
        return mysql_database;
    }

    public void setMysql_database(String mysql_database) {
        this.mysql_database = mysql_database;
    }

    public String getMysql_username() {
        return mysql_username;
    }

    public void setMysql_username(String mysql_username) {
        this.mysql_username = mysql_username;
    }

    public String getMysql_password() {
        return mysql_password;
    }

    public void setMysql_password(String mysql_password) {
        this.mysql_password = mysql_password;
    }

    public String getMysql_dr_table() {
        return mysql_dr_table;
    }

    public void setMysql_dr_table(String mysql_dr_table) {
        this.mysql_dr_table = mysql_dr_table;
    }

    public String getMysql_totals_table() {
        return mysql_totals_table;
    }

    public void setMysql_totals_table(String mysql_totals_table) {
        this.mysql_totals_table = mysql_totals_table;
    }

    public String getMysql_stats_table() {
        return mysql_stats_table;
    }

    public void setMysql_stats_table(String mysql_stats_table) {
        this.mysql_stats_table = mysql_stats_table;
    }

    public String getMysql_reports_table() {
        return mysql_reports_table;
    }

    public void setMysql_reports_table(String mysql_reports_table) {
        this.mysql_reports_table = mysql_reports_table;
    }

    public Integer getApi_port() {
        return api_port;
    }

    public void setApi_port(Integer api_port) {
        this.api_port = api_port;
    }

    public long getLast_loaded_dr() {
        return last_loaded_dr;
    }

    public void setLast_loaded_dr(long last_loaded_dr) {
        this.last_loaded_dr = last_loaded_dr;
    }
}
