package com.example.policy.model;

import java.lang.reflect.Field;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.policy.model.RequestContext;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "match_table")
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String designater;
	private String attributeValue;
	private String op;
	private DataType dataType;

    public enum DataType {
        STRING,
        INTEGER,
        BOOLEAN,
        TIME,
        DOUBLE;
		public static DataType fromInt(int value) {
		    switch (value) {
		        case 0: return STRING;
		        case 1: return INTEGER;
		        case 2: return BOOLEAN;
		        case 3: return TIME;
		        case 4: return DOUBLE;
		        default: throw new IllegalArgumentException("Invalid data type value: " + value);
		    }
		} 
        
    }
    @JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "allOf", referencedColumnName = "id")
    private AllOf allOf;
	
    public void setDataType(int dataTypeValue) {
        this.dataType = DataType.fromInt(dataTypeValue);
    }
    
	public boolean evaluate(RequestContext context) throws Exception {
		Object contextValue = getAttributeValueFromContext(context,designater);
        switch (op) {
            case "equals":
                return attributeValue.equals(contextValue);
            case "not equals":
            	return !attributeValue.equals(contextValue);
            case "greaterThan":
                if (dataType == DataType.INTEGER && contextValue instanceof Integer) {
                    return (Integer) contextValue >Integer.parseInt(attributeValue) ;
                }
                if (dataType == DataType.DOUBLE && contextValue instanceof Double) {
                    return (Double) contextValue > Double.parseDouble(attributeValue) ;
                }
                if (dataType == DataType.TIME && contextValue instanceof Time) {
                    return Time.valueOf((String) contextValue).getTime()  > Time.valueOf(attributeValue).getTime() ;
                }
                break;
            case "greaterThanOrEqual":
                if (dataType == DataType.INTEGER && contextValue instanceof Integer) {
                    return (Integer) contextValue >=Integer.parseInt(attributeValue) ;
                }
                if (dataType == DataType.DOUBLE && contextValue instanceof Double) {
                    return (Double) contextValue >= Double.parseDouble(attributeValue) ;
                }
                if (dataType == DataType.TIME && contextValue instanceof Time) {
                    return Time.valueOf((String) contextValue).getTime()  >= Time.valueOf(attributeValue).getTime() ;
                }
                break;
            case "lessThan":
                if (dataType == DataType.INTEGER && contextValue instanceof Integer) {
                    return (Integer) contextValue < Integer.parseInt(attributeValue);
                }
                if (dataType == DataType.DOUBLE && contextValue instanceof Double) {
                    return (Double) contextValue < Double.parseDouble(attributeValue) ;
                }
                if (dataType == DataType.TIME && contextValue instanceof Time) {
                    return Time.valueOf((String) contextValue).getTime()  < Time.valueOf(attributeValue).getTime() ;
                }
                break;
            case "lessThanOrEqual":
                if (dataType == DataType.INTEGER && contextValue instanceof Integer) {
                    return (Integer) contextValue <= Integer.parseInt(attributeValue);
                }
                if (dataType == DataType.DOUBLE && contextValue instanceof Double) {
                    return (Double) contextValue <= Double.parseDouble(attributeValue) ;
                }
                if (dataType == DataType.TIME && contextValue instanceof Time) {
                    return Time.valueOf((String) contextValue).getTime()  <= Time.valueOf(attributeValue).getTime() ;
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + op);
        }
        return false;
    } 
	private Object getAttributeValueFromContext(RequestContext context, String designator) throws Exception {
	    if (designator.startsWith("subject.")) {
	        return getValueFromObject(context.getSubject(), designator.replace("subject.", ""));
	    } else if (designator.startsWith("resource.")) {
	        return getValueFromObject(context.getResource(), designator.replace("resource.", ""));
	    } else if (designator.startsWith("device.")) {
	        return getValueFromObject(context.getDevice(), designator.replace("device.", ""));
	    } else {
	        throw new IllegalArgumentException("Invalid designator: " + designator);
	    }
	}
	private Object getValueFromObject(Object obj, String fieldName) throws Exception {
	    if (obj == null) {
	        return null;
	    }
	    Field field = obj.getClass().getDeclaredField(fieldName);
	    field.setAccessible(true);
	    return field.get(obj);
	}

}
