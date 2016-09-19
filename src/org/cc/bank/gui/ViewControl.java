package org.cc.bank.gui;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.text.DefaultEditorKit.CutAction;

import org.cc.bank.dao.AdminDao;
import org.cc.bank.exception.MoneyNotEnoughException;
import org.cc.bank.exception.MoneyOverFlowException;
import org.cc.bank.exception.PasswdNotSafeException;
import org.cc.bank.po.Admin;
import org.cc.bank.po.Customer;
import org.cc.bank.po.Person;
import org.cc.bank.service.AdminService;
import org.cc.bank.service.CustomerService;
import org.cc.bank.settings.Settings;
import org.cc.bank.utils.BankCardUtils;
import org.cc.bank.utils.BeanFactory;
import org.cc.bank.utils.IdCardUtils;
import org.cc.bank.utils.ReaderUtils;

/**
 * 视图显示控制
 * @author CC11001100
 *
 */
public class ViewControl {

	//保存引用，方便下面操作
	private BeanFactory beanFactory=BeanFactory.getInstance();
	private AdminService adminService=beanFactory.getBean(AdminService.class);
	private CustomerService customerService=beanFactory.getBean(CustomerService.class);
	
	//负责顾客相关的显示
	public AdminShow aShow=new AdminShow();
	//负责管理员相关的显示
	public CustomerShow cShow=new CustomerShow();
	
	//上次登录过的管理员同志
	private Admin lastAdmin;
	//上次登陆过的顾客上帝
	private Customer lastCustomer;
	
	/**
	 * 显示刚登陆上来的菜单
	 */
	public void showBanner(){
		log("-------------------------------- 银行系统 -----------------------------------------");
		log("                      ****** 1. 管理员 ******                                      ");
		log("                      ****** 2. 顾客      ******                                      ");
		log("----------------------------------------------------------------------------------");
	}
	
	/**
	 * 用户登录(有状态，需要保存)
	 * @param role 登录的角色
	 * @return 是否登录成功
	 */
	public boolean login(LoginRole role){
		
		//读用户名
		String username="";
		while(true){
			username=ReaderUtils.readString(4,"请输入用户名：","请仔细检查并重新输入用户名：");
			if(!username.matches("[a-zA-Z]{1}[0-9a-zA-Z]{3,19}")){
				System.out.println("用户名为4~20位数字或字母，并且不能为数字打头。");
			}else{
				break;
			}
		}

		//读密码
		String passwd="";
		while(true){
			passwd=ReaderUtils.readString(6,"请输入密码：","密码为6位数字：");
			if(!passwd.matches("[0-9]{6}")){
				System.out.println("密码必须为6位数字(ATM机).");
			}else{
				break;
			}
		}
		
		//登录保存状态
		if(role==LoginRole.ADMIN){
			//管理员登录
			Admin admin=adminService.login(username,passwd);
			if(admin!=null){
				lastAdmin=admin;
				return true;
			} else{
				System.out.println("登录管理员账号失败，用户名不存在或密码错误");
				return false;
			}
		}else if(role==LoginRole.CUSTOMER){
			//顾客登录
			Customer c=customerService.login(username,passwd);
			if(c!=null){
				lastCustomer=c;
				return true;
			}else{
				System.out.println("登录账号失败，用户名不存在或密码错误");
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 退出系统
	 */
	public void exit(){
		log("谢谢使用 O(∩_∩)O bye~");
		System.exit(0);
	}
	
	/**
	/**
	 * 内部方法，封装一下sysout以方便打印
	 * @param args
	 */
	private void log(Object ...args){
		log(true,args);
	}
	
	/**
	 *  可以选择打印完是否换行
	 * @param newLine
	 * @param args
	 */
	private void log(Boolean newLine,Object ...args){
		for(Object o:args) System.out.print(o+" ");
		if(newLine) System.out.println();
	}
	
	/**
	 * 顾客相关的显示
	 * @author CC11001100
	 *
	 */
	class CustomerShow{
		
		/**
		 * 顾客存款
		 */
		private void saveMoney(){
			double howmuch=0;
			while(true){
				howmuch=ReaderUtils.readDouble("请输入存款金额：","存款金额为数字，并且小店不接受巨额存款：");
				if(howmuch<=0){
					log("请输入大于0的正数可好.");
				}else if(lastCustomer.getMoney()+howmuch>Settings.MAX_MONEY){
					log("不好意思您的卡盛不了那么多钱...");
				}else{
					break;
				}
			}
			try {
				customerService.saveMeney(lastCustomer,howmuch);
			} catch (MoneyOverFlowException e) {
//				e.printStackTrace();
				log("钱太多了，卡里装不下");
				return ;
			}
			log("存款成功，当前余额：",String.format("%.2f",lastCustomer.getMoney()));
		}
		
		/**
		 * 取钱
		 */
		private void drawMoney(){
			double howmuch=0;
			while(true){
				howmuch=ReaderUtils.readDouble("请输入取款金额：","取款金额为小数：");
				if(howmuch<=0){
					log("取款金额为大于0的数哦");
				}else if(lastCustomer.getMoney()<howmuch){
					log("余额不足，当前余额：",String.format("%.2f",lastCustomer.getMoney()));
				}else{
					break;
				}
			}
			try {
				customerService.drawMoney(lastCustomer,howmuch);
				log("取款成功，当前余额：",String.format("%.2f",lastCustomer.getMoney()));
			} catch (MoneyNotEnoughException e) {
//				e.printStackTrace();
				log("钱不够取，当前余额为: ",String.format("%.2f",lastCustomer.getMoney()));
			}
		}
		
		/**
		 * 显示余额
		 */
		private void showBalance() {
			//因为保存了当钱登录的角色，所以直接取出就可以了
			log("当前余额：",String.format("%.2f",lastCustomer.getMoney()));
		}
		
		/**
		 * 显示顾客菜单
		 */
		public void showCustomerMenu() {
			
			while(true){
				
				log("                                 银行系统[顾客]");
				log("**********************************************************************************");
				log("1.存款                2.取款              3.查询余额                             4.转账                  5.修改密码                  6.退出            ");
				log("**********************************************************************************");
				log();
				
				int choose=ReaderUtils.readNumber("请选择：","请输入有效选项：");
				
				switch(choose){
				case 1:
					saveMoney();
					then();
					break;
				case 2:
					drawMoney();
					then();
					break;
				case 3:
					showBalance();
					then();
					break;
				case 4:
					transferMoney();
					then();
					break;
				case 5:
					changePasswd();
					then();
					break;
				case 6:
					return;
				}
			}
		}
		
		/**
		 * 抽取冗余代码
		 */
		private void then(){
			while(true){
				int choose=ReaderUtils.readNumber("1.返回顾客主菜单     0.退出系统：","1.返回顾客主菜单     0.退出系统：");
				switch(choose){
				case 0:
					exit();
					break;
				case 1:
					return ;
				default:
					//do nothing
				}
			}
		}
		
		/**
		 * 修改密码
		 */
		private void changePasswd() {
			
			//读取原密码
			String oldPasswd="";
			while(true){
				oldPasswd=ReaderUtils.readString(6,"请输入旧密码：","请仔细检查并重新输入旧密码：");
				if(!oldPasswd.matches("[0-9]{6}")){
					System.out.println("密码必须为6位数字(ATM机).");
				}else if(!oldPasswd.equals(lastCustomer.getPasswd())){
					log("旧密码错误，请重新输入.");
				}else{
					break;
				}
			}
			
			//读取新密码
			String newPasswd="";
			while(true){
				newPasswd=ReaderUtils.readString(6,"请输入新密码：","请仔细检查并重新输入新密码：");
				if(!newPasswd.matches("[0-9]{6}")){
					System.out.println("密码必须为6位数字(ATM机).");
				}else if(oldPasswd.equals(newPasswd)){
					log("新旧密码不能相同");
				}else{
					break;
				}
			}
			
			//修改密码
			try {
				customerService.changePasswd(lastCustomer,newPasswd);
				log("密码修改成功");
			} catch (PasswdNotSafeException e) {
//				e.printStackTrace();
				log("密码不够安全，修改失败");
			}
		}

		
		/**
		 * 转账
		 */
		private void transferMoney() {
			
			//读取转入账户
			String toAccount="";
			while(true){
				toAccount=ReaderUtils.readString(19,"请输入转入账号：","请输入正确的转入账号：");
				if(!BankCardUtils.validate(toAccount)){
					log("账户(银行卡号)不合法。（银行卡号有自己的验证规则，请输入合法真实的卡号。）");
				}else if(toAccount.equals(lastCustomer.getAccountNumber())){
					log("自己转自己，城会玩...");
				}else if(customerService.load(toAccount)==null){
					log("输入账户不存在，必须重新输入");
				}else {
					break;
				}
			}
			
			//读取转入金额
			double howmuch=0;
			while(true){
				howmuch=ReaderUtils.readDouble("请输入转入金额：","转入金额必须是数字：");
				if(howmuch<=0){
					log("转入金额必须是大于0的正数.");
				}else if(howmuch>lastCustomer.getMoney()){
					log("账户余额不足，当前余额：",lastCustomer.getMoney());
				}else{
					break;
				}
			}
			
			//转入
			try {
				//内存
				lastCustomer.setMoney(lastCustomer.getMoney()-howmuch);
				//数据库
				customerService.transferMoney(lastCustomer.getAccountNumber(),toAccount,howmuch);
				log("转账成功，当前余额",String.format("%.2f",lastCustomer.getMoney()));
			} catch (MoneyNotEnoughException e) {
//				e.printStackTrace();
				log("钱不够");
				//回滚内存保存对象的数据
				lastCustomer.setMoney(lastCustomer.getMoney()+howmuch);
			} catch (SQLException e) {
//				e.printStackTrace();
				log("由于宇宙中熵的规律太过飘逸，所以导致了本次转账失败(严肃脸).");
			} catch (MoneyOverFlowException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				log("抱歉，对方的卡盛不了那么多钱.");
			}
		}

	}
	
	/**
	 * 管理员相关的显示
	 * @author CC11001100
	 *
	 */
	class AdminShow{
		
		/**
		 * 显示管理员的菜单
		 */
		public void showAdminMenu() {
			while(true){
				log("                                 银行系统[管理员]");
				log("**********************************************************************************");
				log("1.添加顾客                             2.计算储蓄金额                           3.富豪排行榜                         4.退出");
				log("**********************************************************************************");
				log();
				
				int choose=ReaderUtils.readNumber("请选择：","请输入有效选项：");
				
				switch(choose){
				case 1:
					//添加顾客
					addCustomer();
					while(true){
						log(false,"1.返回管理员菜单           0.退出系统：");
						choose=ReaderUtils.readNumber("","");
						if(choose==0){
							exit();
						}else if(choose==1){
							break;
						}
					}
					break;
				case 2:
					//计算储蓄金额
					showTotalMoney();
					while(true){
						log(false,"1.返回登录菜单          2.返回管理员主菜单             3.退出系统：");
						choose=ReaderUtils.readNumber("","");
						if(choose==1){
							return;
						}else if(choose==2){
							break;
						}else if(choose==3){
							exit();
						}
					}
					break;
				case 3:
					//富豪排行榜 
					showRankingList();
					while(true){
						log(false,"1.返回登录菜单          2.返回管理员主菜单             3.退出系统：");
						choose=ReaderUtils.readNumber("","");
						if(choose==1){
							return;
						}else if(choose==2){
							break;
						}else if(choose==3){
							exit();
						}
					}
					break;
				case 4:
					//退出
					return;
				}
			}
		}
		
		/**
		 * 显示兜里有多钱了
		 */
		private void showTotalMoney(){
			double totalMoney=adminService.getTotalMeney();
			log("当前存款总额为：",String.format("%.2f",totalMoney));
		}
		
		/**
		 * 添加顾客
		 */
		private void addCustomer(){
			
			//读入卡号
			String customerNumber="";
			while(true){
				customerNumber=ReaderUtils.readString(19,"请输入顾客银行卡号：","请输入顾客银行卡号：");
				if(!BankCardUtils.validate(customerNumber)){
					log("银行卡号不合法。（银行卡号有自己的验证规则，请输入合法真实的卡号。）");
				}else if(customerService.load(customerNumber)!=null){
					log("卡号已被占用，请重新输入");
				}else{
					break;
				}
			}
			
			//读入姓名
			String name="";
			while(true){
				name=ReaderUtils.readString(4,"请输入顾客姓名：","请输入顾客姓名：");
				if(!name.matches("[a-zA-Z]{1}[0-9a-zA-Z]{3,19}")){
					log("用户名为4~20位数字或字母，并且不能为数字打头。");
				}else if(customerService.loadByName(name)!=null){
					log("用户名已被占用，并且每个用户只能绑定一个银行卡，请核准后再操作。");
				}else{
					break;
				}
			}
			
			//读入身份证号
			String idCard="";
			while(true){
				idCard=ReaderUtils.readString(0,"请输入顾客身份证：","请输入顾客身份证：");
				if(!IdCardUtils.validate(idCard)){
					log("身份证号不合法!(身份证号有其独有的验证方法，so，造假请专业。)");
				}else {
					break;
				}
			}
			
			//读入开户金额
			double money=0;
			while(true){
				money=ReaderUtils.readDouble("请输入顾客开户金额：","开户金额为小数 类型：");
				if(money<0){
					log("不能为负，哪有一开户就欠下一屁股债的....");
				}else if(money>Settings.MAX_MONEY){
					log("不好意思，您的卡盛不了那么多钱...");
				}else{
					break;
				}
			}
			
			//读入初始密码
			String passwd="";
			while(true){
				passwd=ReaderUtils.readString(0,"请输入初始密码(6位数字)：","初始密码应为6位数字：");
				if(!passwd.matches("[0-9]{6}")){
//					System.out.println("密码为数字或字母，并且长度至少为6位，最长为40位（如果你记得住的话...）。");
					System.out.println("初始密码应为6位数字，请确认并重新输入。");
				}else{
					break;
				}
			}
			
			Customer c=new Customer(customerNumber,name,passwd,idCard,money,new Date());
			
			adminService.addCustomer(c);
			
			log("添加成功");
		}
		
		/**
		 * 查看富豪榜
		 */
		private void showRankingList(){
			List<Customer> list=adminService.rankingList();
			System.out.printf("%-4s %-45s %-35s %-8s\n","名次","姓名","身份证号码","金额");
			for(int i=0;i<list.size();i++){
				Customer c=list.get(i);
				System.out.printf("%-3d %-20s %-20s %-9.1f\n",i+1,c.getName(),c.getIdCard(),c.getMoney());
			}
		}
		
	}
	
	/**
	 * 登录类型
	 * @author CC11001100
	 *
	 */
	enum LoginRole{
		ADMIN,  //管理员
		CUSTOMER; //顾客
	}
	
}
