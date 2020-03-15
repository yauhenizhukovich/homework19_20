package com.gmail.supersonicleader.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gmail.supersonicleader.repository.model.StatusEnum;

import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.MAX_NAME_SIZE;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.MIN_NAME_SIZE;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.NAME_REGEXP;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.NAME_REGEXP_MESSAGE;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.NAME_SIZE_MESSAGE;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.NOT_NULL_NAME_MESSAGE;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.NOT_NULL_STATUS_MESSAGE;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.STATUS_ENUM_REGEXP;
import static com.gmail.supersonicleader.service.constant.DocumentDTOConstant.STATUS_VALIDATION_MESSAGE;

public class ItemDTO {

    @NotNull(message = NOT_NULL_NAME_MESSAGE)
    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE, message = NAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_REGEXP, message = NAME_REGEXP_MESSAGE)
    private String name;
    @NotNull(message = NOT_NULL_STATUS_MESSAGE)
    private StatusEnum status;
    private Long id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
