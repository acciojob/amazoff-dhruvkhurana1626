package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        if(orderMap.containsKey(order.getId()) return;
        String id = order.getId();
        orderMap.put(id,order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        if(partnerMap.containsKey(partnerId)) return;
        DeliveryPartner D1 = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,D1);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){

            //add order to given partner's order list
            partnerToOrderMap.putIfAbsent(partnerId,new HashSet<>());
            partnerToOrderMap.get(partnerId).add(orderId);

            //increase order count of partner
            DeliveryPartner p = partnerMap.get(partnerId);
            p.setNumberOfOrders(p.getNumberOfOrders()+1);

            //assign partner to this order
            orderToPartnerMap.put(orderId,partnerId);
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        if(!partnerMap.containsKey(partnerId)) return null;
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        if(!partnerMap.containsKey(partnerId))return 0;
        DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
        return deliveryPartner.getNumberOfOrders();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        if(!partnerToOrderMap.containsKey(partnerId)) return new ArrayList<>();
        return new ArrayList<>(partnerToOrderMap.get(partnerId));
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        if(orderMap.size()==0) return new ArrayList<>();
        List<String> allorders = new ArrayList<>(orderMap.keySet());
        return allorders;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        if(!partnerMap.containsKey(partnerId)) return;
        DeliveryPartner partnerRemoved = partnerMap.remove(partnerId);
        if(!partnerToOrderMap.containsKey(partnerRemoved)) return;
        partnerToOrderMap.remove(partnerRemoved.getId());
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        if(!orderMap.containsKey(orderId)) return;
        Order removedOrder = orderMap.remove(orderId);
        if(!orderToPartnerMap.containsKey(removedOrder)) return;
        orderToPartnerMap.remove(removedOrder.getId());
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
         return orderMap.size()-orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        String[] parts = timeString.split(":");
        int time = Integer.parseInt(parts[0])+Integer.parseInt(parts[1]);
        DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
        HashSet<String> allordersofpartner = partnerToOrderMap.get(deliveryPartner.getId());

        int count = 0;

        for(String orderid : allordersofpartner){
            Order order = orderMap.get(orderid);
            if(!orderToPartnerMap.containsKey(orderid))continue;
            int completionTime = order.getDeliveryTime();
            if(time>completionTime)count++;
        }

        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
        HashSet<String> allordersofpartner = partnerToOrderMap.get(deliveryPartner.getId());

        int lastOrder = 0;

        for(String orderid : allordersofpartner){
            Order order = orderMap.get(orderid);
            int completionTime = order.getDeliveryTime();
            if(lastOrder<completionTime){
                lastOrder = completionTime;
            }
        }

        int hours = lastOrder/60;
        int mins = lastOrder-lastOrder/12;

        return hours+":"+mins;
    }
}