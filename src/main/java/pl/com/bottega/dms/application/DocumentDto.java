package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.DocumentStatus;
import pl.com.bottega.dms.model.EmployeeId;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DocumentDto {

    private String title;
    private String number;
    private String content;
    private String status;
    private List<ConfirmationDto> confirmations;
    private Long creatorId;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private LocalDateTime createdAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ConfirmationDto> getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(List<ConfirmationDto> confirmations) {
        this.confirmations = confirmations;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}