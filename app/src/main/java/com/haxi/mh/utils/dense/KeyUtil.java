package com.haxi.mh.utils.dense;

/**
 * RSA密钥
 * Created by Han on 2018/9/26
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class KeyUtil {

    //服务端的RSA公钥(Base64编码)
    public final static String SERVER_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxIRzupfvIxE9TVksf2VvSb8u0RkpgCQHTsV7LZIgMOPuyHrz1QajUlOH3Ata7G+eEIedEtg+7PpbbvNwbwm7glUfpGS/gytReRiHZTX+iQzeZd0VlQZP02ZHN0vDhodzTQ4A6ID9O0EQ7oqZDw7raJak15kCtwZmeESPPc/DEV8l/cih0i+rfe9b91yMZ2cXPOY0F04MqPTUWTsRmhGE9JwboZyVCp7sKZOrT9F9JqOZCx/9KSRiWV06pRojJ7Pv3S9BbFpAdaR0qabJYbI4qUY7LspQvo0Ma1xUKjHzL4dcJBiyEYQnrEYUnbuZyIkCGn+0ofPhD7yIDJU4NuujUQIDAQAB";

    //服务端的RSA私钥(Base64编码)
    public final static String SERVER_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDEhHO6l+8jET1NWSx/ZW9Jvy7RGSmAJAdOxXstkiAw4+7IevPVBqNSU4fcC1rsb54Qh50S2D7s+ltu83BvCbuCVR+kZL+DK1F5GIdlNf6JDN5l3RWVBk/TZkc3S8OGh3NNDgDogP07QRDuipkPDutolqTXmQK3BmZ4RI89z8MRXyX9yKHSL6t971v3XIxnZxc85jQXTgyo9NRZOxGaEYT0nBuhnJUKnuwpk6tP0X0mo5kLH/0pJGJZXTqlGiMns+/dL0FsWkB1pHSppslhsjipRjsuylC+jQxrXFQqMfMvh1wkGLIRhCesRhSdu5nIiQIaf7Sh8+EPvIgMlTg266NRAgMBAAECggEAW47ydG8xRX9UtmLjrn9N+GgQoe89PKyMkxDDMRox0VWA/Pr7+7ZaBZNRax4HQL1IQZ9NeWgy+Sozy1+0dKduK20Jjwg5ih12mhrpiig77RKH06HL2mDUdEPkzkiIUndtKutpVOo0EZVhFCKS6UckOthk+OYbM7bRqxsWOdBArCh83u/U9yH6M5GeUsKMDCVoSyKV4gXuwLssFcKmzw8VnEnmYQB/K5d37MM7W+2FOKES1l+cu1bC5ZtiJ9fPKNZ3YP+hkkrkKDerI9ZDhm71bWGGIfQ2l3RxpYRq1Mw5J+rIzTKWBL2AgfraxTOhxqA2KZMpyC1nuW5a4n2oQTqMAQKBgQDynOgt8Iybmd7e43GEuXNAFhPGizx1b6LVkFGirfd57AxGiVVKAjU5/WokFo0zbCyydwnzRGansT/J3ggEQ2GjoxXHJZ89LN0GktPTCWNEbtXYPwvHIMxAQotXXA4l4Ui8nJ7sXpOBXyQJQFRDlsXjSTLaIjRVwW5ek0jdGF8ecQKBgQDPXGkY9MdA3+QA4696uGMLHJpQIXD6fG1e8UzdKKcO2FczW6mag2tCbWXGevlTFXI6Emn0RXbdYWr+09Toim4qKJF7mgOmHGUj6StWBLcEwkUpgoNs0/kHXkXXliAQJHm95YRPK5dZOOYs98StUdtLI0CEe9TOmzy0I8JY8uUC4QKBgA18cV+UXoyLuhrTs8y37lMipwjDVqIEiZ2rfw4282nAmFIXcjlB9Cl8D7lf04uHONjWyyPNqpMpxCz5faKXCEdrWluOmNJyuvqRkNrLS8xTCOoTXxk7ln7/8/TwNg0e/8cUQbVL8JEhc8Rb4hM7v1ZKDFyZyCT4MqVpNIStxSPRAoGAfrw1Q9p/Wag9W0nNe4zsg+MUcXsu6PlZg1ipqNXq3Mje6vXQAC7oRG5GcPvCHMSTzuhoN7KpBn8+/MaNbx8EW+H1oPyt1D2wlzUwll9FWLQ/vzIUj1Qy9QzqVOxdZmBUw/peVmEHHLIJBFzeqSvJgu/lw9NHaos4m8TrSKAT1iECgYAXAqPPRnpiCqXF1hEfExeGYZVchG26mIaAZ2o6MBreaLk2d9d/tej2c7r4SbAyJGRlxzTo8XW9zRgdgtqFksx+jvz0a3QTku72RHMcl61upUFu06osolYpCIBsbGUEMAyHvzthAIhUQdH/7u5Zi51ei2q8t59v4Gk4YLjfN2IyZw==";

    //APP端的RSA公钥(Base64编码)
    public final static String APP_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApXszhW+IXlwbov34KOmq2Vis9WkTYcEQCyEIFCGbmPOKFE4OLO1l8DsdlEZLFx72rkBl694vOCWQrFtqdCPVszvTL/Qkhz44GNUyywu+//8hAdAa3O2vxhEj5oybohgJkyol6lKpjp77JxELLSkE0/SciO3ZaqB3q3Fjg2EhNDWEkezNh/NhF1eR+KMAt9DEXWqthpH+/Ai0en9dtUTJx60Xt8flKm+R0xgPinRkz5CPwu9nDDBjW9inqN2SRdDpiacKA5V4S6g/iBc62fGRWItGQAjpvX8k6n2+ifkK8hCHG8L+CvlVgSKZxWEi9CSNAKoD6PykprAcrUuWewcjMQIDAQAB";

    //APP端的RSA私钥(Base64编码)
    public final static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQClezOFb4heXBui/fgo6arZWKz1aRNhwRALIQgUIZuY84oUTg4s7WXwOx2URksXHvauQGXr3i84JZCsW2p0I9WzO9Mv9CSHPjgY1TLLC77//yEB0Brc7a/GESPmjJuiGAmTKiXqUqmOnvsnEQstKQTT9JyI7dlqoHercWODYSE0NYSR7M2H82EXV5H4owC30MRdaq2Gkf78CLR6f121RMnHrRe3x+Uqb5HTGA+KdGTPkI/C72cMMGNb2Keo3ZJF0OmJpwoDlXhLqD+IFzrZ8ZFYi0ZACOm9fyTqfb6J+QryEIcbwv4K+VWBIpnFYSL0JI0AqgPo/KSmsBytS5Z7ByMxAgMBAAECggEBAJ5c/vtNt1WGFDSxk9GjfQsvH6Zj/lZoqm/cFp0dZIjFWHkCEfLQ5eM2z6Hce3QO/8fWfTNymAHFgWRQv9mRiatTL1IO9P4wfwLU1R0Xipv2jqHrhCm1laeZM6ApI6/GDTO2/D35CachYf0LBIUdYi9tQniQFqdCFgQOKUvLojkRDA2jyUhI2yVykdypQ6SiM6ylrVNa234ynDyzOZmusUgfL67zAmRtxjXDBdvZHKCyEs9iGen1Cc9hcL9pzLq+VxLx7wd5TXtueIBLM757G0IdODFTXyGe1sVodF81WOzhHOX0eIcwL1GVLI770a1xgM7A+p7y8wYmrIrb22fA1yECgYEA1y8oJyCKpq8XHvT0h/T/ZbqUAbFlkG7eLAR8jFmUSvtLgN/eZzeYtMLWrzUOPfPTeT74jaRIcFtEs437GE3HRxnbXv+no7F43KWTlypa/7ewPjO9foI/FcRiBOIn7rq2auuKuybXa7K78BMG4ZJnxLnwb1JKrhT5ub3iS1xjfY0CgYEAxN6Vw1wT2rNuSlBuLsaAD0h8wAZqSqTZHc6tYIruOVTjyCzPVreMBIk5wuNmS/qLfx3w4nNpqRJYRxNbDRb5GJDajvOZwjLeIo2ZMvkv4YUQo9rXfJ2e7x9RxMaP03IUipysvqKgHHY9uONsBlJaP192FkIltKsxKb5SiE6Q+TUCgYBQjGJnYxMDmf75o4/1ZIxFtDW6/ICcNCQOLg/BKbcM6kw1DZfe4zzSTSy3oRCXuDGs7Rl1S82h4UgyPi+DxXmW7+LiCKjj6YbocUJyD3TIzCW44v1H4dFjgIOAeHeiMVofY9Qmgj+ZHOqkzzqALaGSTlfmE9PInilskdunggnr8QKBgF6jphkpHX5IPZ/J9H3V/N/b9+ST8Or7Pkh+/4fmoci7z1h+ehn02IGhuju+94FU61Rrf05NLyEQ6ZEZc+7h+VNe50JLRsI9k93Gdjwz4FmojyxF2stUNoA7bSKMVb3PHKLZ2I04PoIQMXG9GkQbuLi4Wlsu51Nn7JVloeVW54h1AoGBAMMjTm7MKs+IUVx4orvUJ098x9HWKTC9oNo20SapLNU05opV1pkMpjPFgS3P4wBrBWG7mkq6q89f07QaQtL6ATaa4RGJyPTztl43SALB/B7w74CW8SYZeSRwubExSPElYIFH/nr0n8vICGufFQEMnBINSM2CmT2Npl51Q1W2XTZc";

    public static final String CONTENT = "{\n" +
            "    \"ak\": \"XGML6ynmxy9zKkOX86S8p1j6hRrTIfXFQaqMxeWiy5xsfFWYEvCqpxSqJiELBKW%2FAdRGjvyerBkdA%2Fm8eaa51iPzHaoLC1SIwIrFAuK436D1oiVsKPy9mHXxlwZRXMvzbr07yOcWfuSbMfrkzK4is3udq1cR6Iz6r%2B%2BCrrYWQ0OeWN3uXxRF%2BB4NxNZE3QW4y3pcrZefsjP%2FBhJTe87CZIbrt7cmxaK%2F2MzidwMQcTLBVFa0Cfrzq3GFCgEmlwdZ%2B%2B6Ky2WQQsPz3s%2BeGsVyB%2F%2F3Desy3INRFR1vyfCjpdsYR4QUQx7OXavOlSkJC5vems%2Bdhz0xAKH1o%2FAtUvyNDg%3D%3D\",\n" +
            "    \"data\": \"qOErMXzVDdOzoCT86nN5lZ5RsXSmNEiluqwuW1g11Mo%3D\"\n" +"   " +
            "    \"act\": \"Tcq%2FBCbkSznMaujJ4zVD9w%3D%3D\"\n" +
            "}";
}
