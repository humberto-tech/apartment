package apartment.data;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class HostFileRepository implements HostRepository{

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;


    public HostFileRepository(@Value("${hostFilepath}")String filePath){
        this.filePath=filePath;
    }
//
//    private String serialize(Item item) {
//        return String.format("%s,%s,%s,%s",
//                item.getId(),
//                item.getName(),
//                item.getCategory(),
//                item.getDollarPerKilogram());
//    }
//
//    private Item deserialize(String[] fields) {
//        Item result = new Item();
//        result.setId(Integer.parseInt(fields[0]));
//        result.setName(fields[1]);
//        result.setCategory(Category.valueOf(fields[2]));
//        result.setDollarPerKilogram(new BigDecimal(fields[3]));
//        return result;
//    }




}
