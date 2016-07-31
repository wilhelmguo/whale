package com.jy.common.utils.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局静态资源：
 *
*/
public class Const {

	public static final Map<Integer, String> SPMC = new HashMap<Integer, String>() {
		{
			put(0, "直属领导审批");
			put(1, "经理审批");
			put(2, "总经理审批");
			put(3, "总经理审批");
			put(4, "总经理审批");
			put(5, "总经理审批");
			put(6, "总经理审批");
			put(7, "总经理审批");
			put(8, "总经理审批");
			put(9, "总经理审批");
			put(10, "总经理审批");
		}
	};

	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_COMPANY = "company";
	public static final String SESSION_MENULIST = "menuList";			//当前菜单
	/**
	 *邮箱配置文件位置
	 */
	public static final String EMAIL_CONFIG="/mail.properties";
	/**
	 *微信配置文件位置
	 */
	public static final String WEIXIN_CONFIG="/weixin/mp.properties";
	/**
	 *上传配置文件位置
	 */
	public static final String UPLOAD_CONFIG="upload.properties";
	/**
	 *没有权限返回的URL
	 */
	public static final String NO_AUTHORIZED_URL="/system/noAuthorized";//没有权限返回的URL
	/**
	 *没有权限返回中文说明
	 */
	public static final String NO_AUTHORIZED_MSG="当前角色没有权限";//
	/**
	 *返回值 没有权限 100
	 */
	public static final int NO_AUTHORIZED=100;
	/**
	 *返回值 成功(1)
	 */
	public static final int SUCCEED = 1;
	/**
	 *返回值 失败(0)
	 */
	public static final int FAIL = 0;
	/**
	 *菜单类型 (1)
	 */
	public static final String RESOURCES_TYPE_MENU = "1";
	/**
	 *功能类型(2)
	 */
	public static final String RESOURCES_TYPE_FUNCTION = "2";
	/**
	 *按钮类型(3)
	 */
	public static final String RESOURCES_TYPE_BUTTON = "3";
	/**
	 *保存成功
	 */
	public static final String SAVE_SUCCEED = "保存成功";
	/**
	 *保存失败
	 */
	public static final String SAVE_FAIL = "保存失败";
	/**
	 *删除成功
	 */
	public static final String DEL_SUCCEED = "删除成功";
	/**
	 *删除失败
	 */
	public static final String DEL_FAIL = "删除失败";
	/**
	 *修改成功
	 */
	public static final String UPDATE_SUCCEED = "修改成功";
	/**
	 *修改失败
	 */
	public static final String UPDATE_FAIL = "修改失败";
	/**
	 *数据获取成功
	 */
	public static final String DATA_SUCCEED = "数据获取成功";
	/**
	 *数据获取失败
	 */
	public static final String DATA_FAIL = "数据获取失败";

	public static final String SUPERUSER = "superuser";

	public static final String MANAGE="管理者";

	public static final String NOMAL_EMPLOYEE="普通员工";

	public static final String[] DEFAULT_ROLES={"管理者","普通员工"};

	/**
	 * 总经办管理者
	 */
	public static final String DEFAULT_ZJB_MANAGER="1,2,ae8fea67b8434987b718c51fe83bb45d,1e2cd5e710224cf0ba09bbe5d4ee0009,fa04b6fe03944f2a9c040dbc9071af9b,83984d22f9da4991833f506695307b6f,89ff6bdf54ca4501a264adba8eb0ee39,15a7d1cec70c47bdbfd41c7f9c694481,3,4c97ca25b7e940948b3d403c3b091c81,d22f22f0bc9a4ad2a6464cca6497a7b4,51aa6b0b037340b9ae88d22d2a354acf,4ee50d1722a34edeb6c15bdbf29e556b,42a2938acb5e4a26b5ec2cc6089b7c2e,5ba75d14f5b74572bd5ff35d12c58060,3a4c4852df564a5bad62e91edb8d9af3,0fc61ec76ab74e00ad3b8f7e16d6e8af,327683eebd794c7aac92acb24b2e7d27,6235cae5aabb499a84a90b539b81e8c4,b07119015ae24630b83d3145cc8752f7,51accc47cfa847d8a017e84dba40abde,4b189c0ae89c4916a0e9364b90103b47,1235cae1aabb498a84a90b531b81e8c4,3b18d4cc525f474aa5ca33e0fea5c721,f39e02a7514311e68732ace0100f8647,f39c8c04514311e68732ace0100f8647,f39b6da4514311e68732ace0100f8647,a15ef7cc16e149d0ab576a577ddb1e9d,7a96190fa8d44baf8023a68ea32afb38,8b15207f4b4611e6b0f0ace0100f8647,8b112bcb4b4611e6b0f0ace0100f8647,8b1279e94b4611e6b0f0ace0100f8647,8b13c3524b4611e6b0f0ace0100f8647,8b169c574b4611e6b0f0ace0100f8647,168dbd444b6511e6b0f0ace0100f8647,a35f9b464b6511e6b0f0ace0100f8647,a35b7e0f4b6511e6b0f0ace0100f8647,a35e38d94b6511e6b0f0ace0100f8647,a36107b54b6511e6b0f0ace0100f8647,a35d0ddb4b6511e6b0f0ace0100f8647,de9fd46098104613be80c460e7451b1e,1962a4b38a3e49f282103a799cf41edb,8016f2420e404f80a5a02de592b6a7c1,b97119015ae24630b83d3145cc8752f7,61accc47cfa847d8a017e84dba40abde,2b189c0ae89c4916a0e9364b90103b47,3a4c4852df564a5bad62e91edb8d4af3,6235cae5aabb499a84a90b539b81e8c3,4916f2420e404f80a5a02de592b6a7c1,0962a4b38a3e49f282103a799cf41edb,3b189c0ae89c4916a0e9364b90103b47,b27119015ae24630b83d3145cc8752f7,54accc47cfa847d8a017e84dba40abde,de9fd46098104613be80c460e7451b0e,b1adb204519b11e68732ace0100f8647,b1a89b28519b11e68732ace0100f8647,b1b07ad9519b11e68732ace0100f8647,b1af2b34519b11e68732ace0100f8647,b1ac8047519b11e68732ace0100f8647,58230413e8364b67af2bb65e64252260,b776518dfdd940e394a15de3974a8610,52910f91bf3e4b71af4ded1ef67bba48,18756082926f41329e51d74dda3abc80,4021171e506b47c083c77a2b96660031,a201aa381c3447aeaae23bdb478b3a06,b8203143736e4e549b2e0fc3c617d4c4,55e7a2d527564d9790b9f00a00d34634,17325e24f35348fd9a287152e3ea8b75,7537535a8b5d44009f52a3e84ca5dfd8,c9c822f6c7b94650a6f3944c53738caf,47ff0c409f314b07b52ba13f1bb81586,72cbdeff2aea4aea8f348bb6d779a87d,e750134d22094ef3a611fe8300c351e9,a103f06e9c7f4e8aafc5eaf4c735fd91,dc4a83f740f54f6b89916321968cbcd3,9bc994928cc64fdca432fcad2a0b3ba9,1d906f69a7274cbd8b316903e66fc580,9cff3045dd824773a70c5a887a2f71ef,f541a86e629d4eefb014309c921a237e,e49f8b358da2404293f6ac4e7f7a7a30";

	/**
	 * 人力资源管理者
	 */
	public static final String DEFAULT_HR_MANAGER="3a4c4852df564a5bad62e91edb8d9af3,0fc61ec76ab74e00ad3b8f7e16d6e8af,327683eebd794c7aac92acb24b2e7d27,6235cae5aabb499a84a90b539b81e8c4,b07119015ae24630b83d3145cc8752f7,51accc47cfa847d8a017e84dba40abde,4b189c0ae89c4916a0e9364b90103b47,1235cae1aabb498a84a90b531b81e8c4,3b18d4cc525f474aa5ca33e0fea5c721,f39e02a7514311e68732ace0100f8647,f39c8c04514311e68732ace0100f8647,f39b6da4514311e68732ace0100f8647,a15ef7cc16e149d0ab576a577ddb1e9d,7a96190fa8d44baf8023a68ea32afb38,8b15207f4b4611e6b0f0ace0100f8647,8b112bcb4b4611e6b0f0ace0100f8647,8b1279e94b4611e6b0f0ace0100f8647,8b13c3524b4611e6b0f0ace0100f8647,8b169c574b4611e6b0f0ace0100f8647,168dbd444b6511e6b0f0ace0100f8647,a35f9b464b6511e6b0f0ace0100f8647,a35b7e0f4b6511e6b0f0ace0100f8647,a35e38d94b6511e6b0f0ace0100f8647,a36107b54b6511e6b0f0ace0100f8647,a35d0ddb4b6511e6b0f0ace0100f8647,de9fd46098104613be80c460e7451b1e,1962a4b38a3e49f282103a799cf41edb,8016f2420e404f80a5a02de592b6a7c1,b97119015ae24630b83d3145cc8752f7,61accc47cfa847d8a017e84dba40abde,2b189c0ae89c4916a0e9364b90103b47,3a4c4852df564a5bad62e91edb8d4af3,6235cae5aabb499a84a90b539b81e8c3,0962a4b38a3e49f282103a799cf41edb,54accc47cfa847d8a017e84dba40abde,e750134d22094ef3a611fe8300c351e9,a103f06e9c7f4e8aafc5eaf4c735fd91,dc4a83f740f54f6b89916321968cbcd3,9bc994928cc64fdca432fcad2a0b3ba9,1d906f69a7274cbd8b316903e66fc580,9cff3045dd824773a70c5a887a2f71ef,f541a86e629d4eefb014309c921a237e,e49f8b358da2404293f6ac4e7f7a7a30";
	/**
	 * 市场部理者
	 */
	public static final String DEFAULT_SCB_MANAGER="3a4c4852df564a5bad62e91edb8d9af3,0fc61ec76ab74e00ad3b8f7e16d6e8af,327683eebd794c7aac92acb24b2e7d27,6235cae5aabb499a84a90b539b81e8c4,b07119015ae24630b83d3145cc8752f7,51accc47cfa847d8a017e84dba40abde,4b189c0ae89c4916a0e9364b90103b47,78886133548011e6a40e54ee75456125,d77c9723548011e6a40e54ee75456125,d781ec80548011e6a40e54ee75456125,d780496d548011e6a40e54ee75456125,d77e2c80548011e6a40e54ee75456125,3a4c4852df564a5bad62e91edb8d4af3,6235cae5aabb499a84a90b539b81e8c3,0962a4b38a3e49f282103a799cf41edb,54accc47cfa847d8a017e84dba40abde,e750134d22094ef3a611fe8300c351e9,a103f06e9c7f4e8aafc5eaf4c735fd91,dc4a83f740f54f6b89916321968cbcd3,9bc994928cc64fdca432fcad2a0b3ba9,1d906f69a7274cbd8b316903e66fc580,9cff3045dd824773a70c5a887a2f71ef,f541a86e629d4eefb014309c921a237e,e49f8b358da2404293f6ac4e7f7a7a30";
	/**
	 * 普通部门管理者
	 */
	public static final String DEFAULT_NOMAL_MANAGER="3a4c4852df564a5bad62e91edb8d9af3,0fc61ec76ab74e00ad3b8f7e16d6e8af,327683eebd794c7aac92acb24b2e7d27,6235cae5aabb499a84a90b539b81e8c4,b07119015ae24630b83d3145cc8752f7,51accc47cfa847d8a017e84dba40abde,4b189c0ae89c4916a0e9364b90103b47,78886133548011e6a40e54ee75456125,d77c9723548011e6a40e54ee75456125,d781ec80548011e6a40e54ee75456125,d780496d548011e6a40e54ee75456125,d77e2c80548011e6a40e54ee75456125,3a4c4852df564a5bad62e91edb8d4af3,6235cae5aabb499a84a90b539b81e8c3,54accc47cfa847d8a017e84dba40abde,e750134d22094ef3a611fe8300c351e9,a103f06e9c7f4e8aafc5eaf4c735fd91,dc4a83f740f54f6b89916321968cbcd3,9bc994928cc64fdca432fcad2a0b3ba9,1d906f69a7274cbd8b316903e66fc580,9cff3045dd824773a70c5a887a2f71ef,f541a86e629d4eefb014309c921a237e,e49f8b358da2404293f6ac4e7f7a7a30";
	/**
	 * 普通员工权限
	 */
	public static final String DEFAULT_NOMAL_EMPLOYEE="3a4c4852df564a5bad62e91edb8d9af3,0fc61ec76ab74e00ad3b8f7e16d6e8af,327683eebd794c7aac92acb24b2e7d27,6235cae5aabb499a84a90b539b81e8c4,b07119015ae24630b83d3145cc8752f7,51accc47cfa847d8a017e84dba40abde,4b189c0ae89c4916a0e9364b90103b47,3a4c4852df564a5bad62e91edb8d4af3,6235cae5aabb499a84a90b539b81e8c3,54accc47cfa847d8a017e84dba40abde,e750134d22094ef3a611fe8300c351e9,a103f06e9c7f4e8aafc5eaf4c735fd91,dc4a83f740f54f6b89916321968cbcd3,9bc994928cc64fdca432fcad2a0b3ba9,1d906f69a7274cbd8b316903e66fc580,9cff3045dd824773a70c5a887a2f71ef,f541a86e629d4eefb014309c921a237e,e49f8b358da2404293f6ac4e7f7a7a30";

  /**
   * 公司所有权限
   *
   */
  public static final String COMPANY_AUTH="3a4c4852df564a5bad62e91edb8d9af3,0fc61ec76ab74e00ad3b8f7e16d6e8af,327683eebd794c7aac92acb24b2e7d27,6235cae5aabb499a84a90b539b81e8c4,b07119015ae24630b83d3145cc8752f7,51accc47cfa847d8a017e84dba40abde,4b189c0ae89c4916a0e9364b90103b47,78886133548011e6a40e54ee75456125,d77c9723548011e6a40e54ee75456125,d780496d548011e6a40e54ee75456125,d77e2c80548011e6a40e54ee75456125,d781ec80548011e6a40e54ee75456125,1235cae1aabb498a84a90b531b81e8c4,3b18d4cc525f474aa5ca33e0fea5c721,f39b6da4514311e68732ace0100f8647,f39e02a7514311e68732ace0100f8647,f39c8c04514311e68732ace0100f8647,a15ef7cc16e149d0ab576a577ddb1e9d,168dbd444b6511e6b0f0ace0100f8647,a35f9b464b6511e6b0f0ace0100f8647,a35b7e0f4b6511e6b0f0ace0100f8647,a36107b54b6511e6b0f0ace0100f8647,a35d0ddb4b6511e6b0f0ace0100f8647,a35e38d94b6511e6b0f0ace0100f8647,7a96190fa8d44baf8023a68ea32afb38,8b15207f4b4611e6b0f0ace0100f8647,8b112bcb4b4611e6b0f0ace0100f8647,8b13c3524b4611e6b0f0ace0100f8647,8b169c574b4611e6b0f0ace0100f8647,8b1279e94b4611e6b0f0ace0100f8647,de9fd46098104613be80c460e7451b1e,1962a4b38a3e49f282103a799cf41edb,8016f2420e404f80a5a02de592b6a7c1,b97119015ae24630b83d3145cc8752f7,61accc47cfa847d8a017e84dba40abde,2b189c0ae89c4916a0e9364b90103b47,1,2,ae8fea67b8434987b718c51fe83bb45d,1e2cd5e710224cf0ba09bbe5d4ee0009,fa04b6fe03944f2a9c040dbc9071af9b,83984d22f9da4991833f506695307b6f,89ff6bdf54ca4501a264adba8eb0ee39,15a7d1cec70c47bdbfd41c7f9c694481,3,4c97ca25b7e940948b3d403c3b091c81,d22f22f0bc9a4ad2a6464cca6497a7b4,51aa6b0b037340b9ae88d22d2a354acf,4ee50d1722a34edeb6c15bdbf29e556b,42a2938acb5e4a26b5ec2cc6089b7c2e,5ba75d14f5b74572bd5ff35d12c58060,3a4c4852df564a5bad62e91edb8d4af3,6235cae5aabb499a84a90b539b81e8c3,0962a4b38a3e49f282103a799cf41edb,4916f2420e404f80a5a02de592b6a7c1,54accc47cfa847d8a017e84dba40abde,b27119015ae24630b83d3145cc8752f7,3b189c0ae89c4916a0e9364b90103b47,de9fd46098104613be80c460e7451b0e,b1a89b28519b11e68732ace0100f8647,b1adb204519b11e68732ace0100f8647,b1b07ad9519b11e68732ace0100f8647,b1ac8047519b11e68732ace0100f8647,b1af2b34519b11e68732ace0100f8647,e750134d22094ef3a611fe8300c351e9,a103f06e9c7f4e8aafc5eaf4c735fd91,dc4a83f740f54f6b89916321968cbcd3,9bc994928cc64fdca432fcad2a0b3ba9,1d906f69a7274cbd8b316903e66fc580,9cff3045dd824773a70c5a887a2f71ef,f541a86e629d4eefb014309c921a237e,e49f8b358da2404293f6ac4e7f7a7a30,58230413e8364b67af2bb65e64252260,b776518dfdd940e394a15de3974a8610,52910f91bf3e4b71af4ded1ef67bba48,18756082926f41329e51d74dda3abc80,4021171e506b47c083c77a2b96660031,a201aa381c3447aeaae23bdb478b3a06,b8203143736e4e549b2e0fc3c617d4c4,55e7a2d527564d9790b9f00a00d34634,17325e24f35348fd9a287152e3ea8b75,7537535a8b5d44009f52a3e84ca5dfd8,c9c822f6c7b94650a6f3944c53738caf,47ff0c409f314b07b52ba13f1bb81586,72cbdeff2aea4aea8f348bb6d779a87d";
}
