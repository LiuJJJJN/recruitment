package com.ibm.rms.domain;

/**
 * 邮件发送内容
 *
 * @author 刘嘉宁
 * @date 2022-12-21 11:04:32
 */
public class Mail {

    // 发送邮箱
    private String from;

    // 接收邮箱
    private String to;

    // 主题
    private String subject;

    // 内容
    private String text;

    public Mail() {

    }

    public Mail(String from, String to, String subject, String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
