package com.jdc.online.balances.model.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.jdc.online.balances.controller.member.dto.ChartAmountVo;
import com.jdc.online.balances.model.BaseRepository;
import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.model.entity.embeddables.LedgerEntryPk;

public interface LedgerEntryRepo extends BaseRepository<LedgerEntry, LedgerEntryPk> {
	
	@Query(value = "select e from LedgerEntry e where e.id.memberId = :id and e.id.issuedDate = :issuedDate and e.id.seqNumber > :seqNumber")
	List<LedgerEntry> findRemainingEntries(long id, LocalDate issuedDate, int seqNumber);

	@Query(value = """
			select sum(e.amount) from LedgerEntry e where 
			e.ledger.member.account.userName = :username and e.ledger.type = :type
			and e.id.issuedDate >= :from and e.id.issuedDate <= :to
			""")
	BigDecimal getSummaryData(BalanceType type, String username, LocalDate from, LocalDate to);

	@Query(value = """
			select new com.jdc.online.balances.controller.member.dto.ChartAmountVo(e.ledger.name, sum(e.amount)) from LedgerEntry e where
			e.ledger.member.account.userName = :username and
			e.ledger.type = :balanceType and
			e.id.issuedDate >= :from and e.id.issuedDate <= :to
			group by e.ledger.name  
			""")
	List<ChartAmountVo> findLedgerData(BalanceType balanceType, String username, LocalDate from, LocalDate to);

}
