package org.cc.bank.gui;

import org.cc.bank.gui.ViewControl.LoginRole;
import org.cc.bank.utils.ReaderUtils;

/**
 * 程序入口
 * @author CC11001100
 *
 */
public class Main {

	public static void main(String[] args) {
		
		ViewControl view=new ViewControl();
		
		while(true){
			
			view.showBanner();
			int choose=ReaderUtils.readNumber("请选择：","请输入有效选项：");
			
			switch(choose){
			case 1:
				if(!view.login(LoginRole.ADMIN)) continue;
				view.aShow.showAdminMenu();
				break;
			case 2:
				if(!view.login(LoginRole.CUSTOMER)) continue ;
				view.cShow.showCustomerMenu();
				break;
			default:
				System.out.println("无效选项，重新选择!");
			}
		}
		
	}
	
}
