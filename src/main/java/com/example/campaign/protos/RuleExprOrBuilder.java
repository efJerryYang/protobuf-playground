// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: campaign.proto
// Protobuf Java Version: 4.27.2

package com.example.campaign.protos;

public interface RuleExprOrBuilder extends
    // @@protoc_insertion_point(interface_extends:campaign.RuleExpr)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.campaign.RuleNode rule_node = 1;</code>
   * @return Whether the ruleNode field is set.
   */
  boolean hasRuleNode();
  /**
   * <code>.campaign.RuleNode rule_node = 1;</code>
   * @return The ruleNode.
   */
  com.example.campaign.protos.RuleNode getRuleNode();
  /**
   * <code>.campaign.RuleNode rule_node = 1;</code>
   */
  com.example.campaign.protos.RuleNodeOrBuilder getRuleNodeOrBuilder();

  /**
   * <code>.campaign.RuleOpExpr rule_op = 2;</code>
   * @return Whether the ruleOp field is set.
   */
  boolean hasRuleOp();
  /**
   * <code>.campaign.RuleOpExpr rule_op = 2;</code>
   * @return The ruleOp.
   */
  com.example.campaign.protos.RuleOpExpr getRuleOp();
  /**
   * <code>.campaign.RuleOpExpr rule_op = 2;</code>
   */
  com.example.campaign.protos.RuleOpExprOrBuilder getRuleOpOrBuilder();

  com.example.campaign.protos.RuleExpr.ExprCase getExprCase();
}
