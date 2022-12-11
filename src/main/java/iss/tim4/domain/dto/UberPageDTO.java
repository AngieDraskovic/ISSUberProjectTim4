package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UberPageDTO<E> {
    private Long totalCount;
    private List<E> results;

    public UberPageDTO(Page<E> page) {
        totalCount = page.getTotalElements();
        results = page.getContent();
    }
}
