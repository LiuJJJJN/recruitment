package com.ibm.rms.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.rms.pojo.DictionaryValue;

import java.util.List;

/**
 * 面试一览画面分页和模糊搜索数据
 *
 * @author 刘嘉宁
 * @date 2022-12-11 03:02:27
 */
public class InterviewOverviewPageConditionDto {
    private String vagueSelect;
    private String vagueInput;

    // springboot 默认按照小驼峰解析、所以这里要注明
    @JsonProperty("IVStatusList")
    private List<DictionaryValue> IVStatusList;
    private List<DictionaryValue> entryTimeList;
    private List<DictionaryValue> gradTimeList;
    private String startGradYear;
    private String endGradYear;
    private Integer pageNo;
    private Integer pageSize;

    public InterviewOverviewPageConditionDto() {

    }

    public InterviewOverviewPageConditionDto(
            String vagueSelect, String vagueInput, List<DictionaryValue> IVStatusList,
            List<DictionaryValue> entryTimeList, List<DictionaryValue> gradTimeList,
            String startGradYear, String endGradYear, Integer pageNo, Integer pageSize
    ) {
        this.vagueSelect = vagueSelect;
        this.vagueInput = vagueInput;
        this.IVStatusList = IVStatusList;
        this.entryTimeList = entryTimeList;
        this.gradTimeList = gradTimeList;
        this.startGradYear = startGradYear;
        this.endGradYear = endGradYear;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getVagueSelect() {
        return vagueSelect;
    }

    public void setVagueSelect(String vagueSelect) {
        this.vagueSelect = vagueSelect;
    }

    public String getVagueInput() {
        return vagueInput;
    }

    public void setVagueInput(String vagueInput) {
        this.vagueInput = vagueInput;
    }

    public List<DictionaryValue> getIVStatusList() {
        return IVStatusList;
    }

    public void setIVStatusList(List<DictionaryValue> IVStatusList) {
        this.IVStatusList = IVStatusList;
    }

    public List<DictionaryValue> getEntryTimeList() {
        return entryTimeList;
    }

    public void setEntryTimeList(List<DictionaryValue> entryTimeList) {
        this.entryTimeList = entryTimeList;
    }

    public List<DictionaryValue> getGradTimeList() {
        return gradTimeList;
    }

    public void setGradTimeList(List<DictionaryValue> gradTimeList) {
        this.gradTimeList = gradTimeList;
    }

    public String getStartGradYear() {
        return startGradYear;
    }

    public void setStartGradYear(String startGradYear) {
        this.startGradYear = startGradYear;
    }

    public String getEndGradYear() {
        return endGradYear;
    }

    public void setEndGradYear(String endGradYear) {
        this.endGradYear = endGradYear;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "InterviewOverviewPageConditionDto{" +
                "vagueSelect='" + vagueSelect + '\'' +
                ", vagueInput='" + vagueInput + '\'' +
                ", IVStatusList=" + IVStatusList +
                ", entryTimeList=" + entryTimeList +
                ", gradTimeList=" + gradTimeList +
                ", startGradYear='" + startGradYear + '\'' +
                ", endGradYear='" + endGradYear + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }

}
