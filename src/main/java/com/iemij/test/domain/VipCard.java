package com.iemij.test.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "vip_card")
public class VipCard {
    @Id
    private Long vipCardId;
    private String vipCardName;
    private double price;
    private Integer courseNum;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public Long getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(Long vipCardId) {
        this.vipCardId = vipCardId;
    }

    public String getVipCardName() {
        return vipCardName;
    }

    public void setVipCardName(String vipCardName) {
        this.vipCardName = vipCardName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
