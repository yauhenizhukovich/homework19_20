package com.gmail.supersonicleader.service.constant;

public interface DocumentDTOConstant {

    int MIN_NAME_SIZE = 3;
    int MAX_NAME_SIZE = 40;
    String NAME_REGEXP = "^[\\w\\d ,.]*$";
    String STATUS_ENUM_REGEXP = "(READY)|(COMPLETED)";

    String NOT_NULL_NAME_MESSAGE = "Name cannot be empty";
    String NOT_NULL_STATUS_MESSAGE = "Status cannot be empty";
    String NAME_SIZE_MESSAGE = "Name length should be from 3 to 40";
    String NAME_REGEXP_MESSAGE = "Name can contain only letters, digits, spaces and punctuation marks, like commas and dots";
    String STATUS_VALIDATION_MESSAGE = "This status is not allowed";

}
