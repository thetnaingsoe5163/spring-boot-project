package com.jdc.online.balances.model.entity.embeddables;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jdc.online.balances.model.entity.LedgerEntrySeq;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerEntryPk {

	private long memberId;
	private LocalDate issuedDate;
	private int seqNumber;
	
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyyMMdd");
	
	public String getCode() {
		return "%s%03d".formatted(issuedDate.format(DF), seqNumber);
	}
	
	public static LedgerEntryPk parse(String id, long memberId) {
		var issuedDate = LocalDate.parse(id.substring(0, 8), DF);
		var seqNumber = Integer.parseInt(id.substring(8));
		
		var pk = new LedgerEntryPk();
		pk.setMemberId(memberId);
		pk.setIssuedDate(issuedDate);
		pk.setSeqNumber(seqNumber);
		
		return pk;
	}

	public static LedgerEntryPk from(LedgerEntrySeq ledgerEntrySeq) {
		var pk = new LedgerEntryPk();
		pk.setMemberId(ledgerEntrySeq.getId().getMemberId());
		pk.setIssuedDate(ledgerEntrySeq.getId().getIssuedAt());
		pk.setSeqNumber(ledgerEntrySeq.getSeqNumber());
		
		return pk;
	}
}