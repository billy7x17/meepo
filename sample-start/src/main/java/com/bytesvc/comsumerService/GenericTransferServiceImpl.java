package com.bytesvc.comsumerService;

import com.bytesvc.ServiceException;
import com.bytesvc.service.IAccountService;
import com.bytesvc.service.ITransferService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("genericTransferService")
public class GenericTransferServiceImpl implements ITransferService {

	@javax.annotation.Resource(name = "jdbcTemplate2")
	private JdbcTemplate jdbcTemplate;
	@org.springframework.beans.factory.annotation.Qualifier("remoteAccountService")
	@org.springframework.beans.factory.annotation.Autowired(required = false)
	private IAccountService remoteAccountService;

	@Transactional(rollbackFor = ServiceException.class)
	public void transfer(String sourceAcctId, String targetAcctId, double amount) throws ServiceException {

		this.increaseAmount(targetAcctId, amount);
		this.remoteAccountService.decreaseAmount(sourceAcctId, amount);


//		 throw new ServiceException("rollback");
	}

	private void increaseAmount(String acctId, double amount) throws ServiceException {
		int value = this.jdbcTemplate.update("update tb_account_two set amount = amount + "+amount+" where acct_id = '"+acctId+"'");
//		int value = this.jdbcTemplate.update("delete from tb_account_two where amount = ? and acct_id = ?", 233.3, "1531315150148");
//		int value = this.jdbcTemplate.update("insert into apple (name)values('liming')");
//		String sql = "select name from apple where id =1  lock in  share mode ";
//		 //        调用方法获得记录数
//		String count = jdbcTemplate.queryForObject(sql, String.class);
//		System.out.println("count="+count);
		System.out.printf("exec increase: acct= %s, amount= %7.2f%n", acctId, amount);
	}

}
