package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.example.campaign.protos.Rule;
import com.example.campaign.protos.RuleExpr;
import com.example.campaign.protos.RuleNode;
import com.example.campaign.protos.RuleOp;
import com.example.campaign.protos.RuleOpExpr;
import com.example.campaign.protos.RuleType;
import com.example.tutorial.AddPeople;
import com.example.tutorial.ListPeople;
import com.example.tutorial.protos.*;

public class App {
    public static void main(String[] args) throws IOException {
        // System.out.println("########### Build Person ###########");
        // Person john = Person.newBuilder()
        // .setId(1234)
        // .setName("John Doe")
        // .setEmail("jdoe@example.com")
        // .addPhones(
        // Person.PhoneNumber.newBuilder()
        // .setNumber("555-4321")
        // .setType(Person.PhoneType.PHONE_TYPE_HOME)
        // .build())
        // .build();
        // System.out.println(john);
        // System.out.println(john.isInitialized());
        // byte[] btarr = john.toByteArray();
        // System.out.println(btarr);
        // try {
        // Person john2 = Person.parseFrom(btarr);
        // System.out.println(john2);
        // } catch (Exception e) {
        // System.out.println(e);
        // }

        // System.out.println("########### Build AddressBook ###########");
        // AddressBook.Builder addressBook = AddressBook.newBuilder();
        // // addressbook path
        // String addressBookPath = "addressbook.data";
        // try {
        // addressBook.mergeFrom(new FileInputStream(addressBookPath));
        // } catch (FileNotFoundException e) {
        // System.out.println("addressbook.data not found, creating new one");
        // }

        // addressBook.addPeople(
        // AddPeople.PromptForAddress(new BufferedReader(new
        // InputStreamReader(System.in)),
        // System.out));

        // addressBook.addPeople(john);
        // FileOutputStream fos = new FileOutputStream(addressBookPath);
        // addressBook.build().writeTo(fos);
        // fos.close();

        // System.out.println("########### Read AddressBook ###########");

        // AddressBook addressBook2 = AddressBook.parseFrom(new
        // FileInputStream(addressBookPath));
        // ListPeople.Print(addressBook2);

        run();
    }

    public static void run() {
        // RuleA = ( Rule0 && Rule1) || Rule3 || ((Rule4 && Rule5) || Rule6)
        RuleNode rule0 = RuleNode.newBuilder()
                .setType(RuleType.BATCH)
                .setDescription("Rule0")
                .build();

        RuleNode rule1 = RuleNode.newBuilder()
                .setType(RuleType.REALTIME)
                .setDescription("Rule1")
                .build();

        RuleNode rule3 = RuleNode.newBuilder()
                .setType(RuleType.BATCH)
                .setDescription("Rule3")
                .build();

        RuleNode rule4 = RuleNode.newBuilder()
                .setType(RuleType.REALTIME)
                .setDescription("Rule4")
                .build();

        RuleNode rule5 = RuleNode.newBuilder()
                .setType(RuleType.BATCH)
                .setDescription("Rule5")
                .build();

        RuleNode rule6 = RuleNode.newBuilder()
                .setType(RuleType.REALTIME)
                .setDescription("Rule6")
                .build();

        RuleExpr rule4AndRule5 = RuleExpr.newBuilder()
                .setRuleOp(RuleOpExpr.newBuilder()
                        .setOp(RuleOp.AND)
                        .setLeft(RuleExpr.newBuilder().setRuleNode(rule4).build())
                        .setRight(RuleExpr.newBuilder().setRuleNode(rule5).build())
                        .build())
                .build();

        RuleExpr nestedPart = RuleExpr.newBuilder()
                .setRuleOp(RuleOpExpr.newBuilder()
                        .setOp(RuleOp.OR)
                        .setLeft(rule4AndRule5)
                        .setRight(RuleExpr.newBuilder().setRuleNode(rule6).build())
                        .build())
                .build();

        RuleExpr rule0AndRule1 = RuleExpr.newBuilder()
                .setRuleOp(RuleOpExpr.newBuilder()
                        .setOp(RuleOp.AND)
                        .setLeft(RuleExpr.newBuilder().setRuleNode(rule0).build())
                        .setRight(RuleExpr.newBuilder().setRuleNode(rule1).build())
                        .build())
                .build();

        RuleExpr rule0AndRule1OrRule3 = RuleExpr.newBuilder()
                .setRuleOp(RuleOpExpr.newBuilder()
                        .setOp(RuleOp.OR)
                        .setLeft(rule0AndRule1)
                        .setRight(RuleExpr.newBuilder().setRuleNode(rule3).build())
                        .build())
                .build();

        RuleExpr finalExpr = RuleExpr.newBuilder()
                .setRuleOp(RuleOpExpr.newBuilder()
                        .setOp(RuleOp.OR)
                        .setLeft(rule0AndRule1OrRule3)
                        .setRight(nestedPart)
                        .build())
                .build();

        Rule ruleA = Rule.newBuilder()
                .setExpr(finalExpr)
                .build();

        String prettyRule = prettyPrintRuleExpr(ruleA.getExpr());
        System.out.println("RuleA = " + prettyRule);
        List<RuleNode> ruleNodes = getAllRuleNodes(ruleA.getExpr());
        System.out.println("RuleNodes:");
        for (RuleNode ruleNode : ruleNodes) {
            System.out.println("  " + ruleNode.getDescription());
        }
    }

    private static String prettyPrintRuleExpr(RuleExpr expr) {
        switch (expr.getExprCase()) {
            case RULE_NODE:
                return expr.getRuleNode().getDescription();
            case RULE_OP:
                RuleOpExpr opExpr = expr.getRuleOp();
                String left = prettyPrintRuleExpr(opExpr.getLeft());
                String right = prettyPrintRuleExpr(opExpr.getRight());
                String operator = (opExpr.getOp() == RuleOp.AND) ? " && " : " || ";
                return "(" + left + operator + right + ")";
            default:
                throw new IllegalArgumentException("Unknown RuleExpr case: " + expr.getExprCase());
        }
    }

    private static List<RuleNode> getAllRuleNodes(RuleExpr expr) {
        List<RuleNode> ruleNodes = new ArrayList<>();
        collectRuleNodes(expr, ruleNodes);
        return ruleNodes;
    }

    private static void collectRuleNodes(RuleExpr expr, List<RuleNode> ruleNodes) {
        switch (expr.getExprCase()) {
            case RULE_NODE:
                ruleNodes.add(expr.getRuleNode());
                break;
            case RULE_OP:
                RuleOpExpr opExpr = expr.getRuleOp();
                collectRuleNodes(opExpr.getLeft(), ruleNodes);
                collectRuleNodes(opExpr.getRight(), ruleNodes);
                break;
            default:
                throw new IllegalArgumentException("Unknown RuleExpr case: " + expr.getExprCase());
        }
    }
}
