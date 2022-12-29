package pt.bayonne.sensei.customer.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FirstName {
    private String value;

    private FirstName(String value){
        this.value = value;
    }

    public static FirstName of(final String value){
        Objects.requireNonNull(value,"the value of first name cannot be null");
        Assert.isTrue(!value.isBlank(),"the value of the first name cannot be blank");
        return new FirstName(value);
    }

}