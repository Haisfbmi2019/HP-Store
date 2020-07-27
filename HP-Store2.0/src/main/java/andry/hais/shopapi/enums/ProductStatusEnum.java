package andry.hais.shopapi.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(1, "Available"),
    DOWN(0, "Unavailable");
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getStatus(Integer code) {

        for (ProductStatusEnum statusEnum : ProductStatusEnum.values()) {
            if (statusEnum.getCode().equals(code))
                return statusEnum.getMessage();
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }
}
