package com.ai_study_group.ia_analyzer.dto;

import com.ai_study_group.ia_analyzer.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryDTO {

	private BigDecimal amount;
	private OperationType operationType;
	private Long branch;
	private Long account;
}
