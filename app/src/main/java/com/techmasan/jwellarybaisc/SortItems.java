package com.techmasan.jwellarybaisc;

import com.techmasan.jwellarybaisc.networkConfig.data.OrderHistoryOrderValue;
import com.techmasan.jwellarybaisc.networkConfig.data.OrderHistoryResponse;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;

public class SortItems  implements Comparator<OrderHistoryResponse> {
    @Override
    public int compare(OrderHistoryResponse orderHistoryResponse, OrderHistoryResponse t1) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String text = orderHistoryResponse.getOrderDate();
            Date date1 = formatter.parse(text);
            String text2 = t1.getOrderDate();
            Date date2 = formatter.parse(text2);
            return date2.compareTo(date1);
        }
        catch (Exception e){

        }
        return orderHistoryResponse.getOrderDate().compareTo(t1.getOrderDate());

    }
}
