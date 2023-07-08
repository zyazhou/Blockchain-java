package com.blockchain.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blockchain.common.lang.Const;
import com.blockchain.common.lang.Result;
import com.blockchain.entity.Blockprojectinfo;
import com.blockchain.entity.Projectinfo;
import com.blockchain.entity.User;
import com.blockchain.mapper.ProjectinfoMapper;
import com.blockchain.mapper.UserMapper;
import com.blockchain.service.BlockprojectinfoService;
import com.blockchain.service.ProjectinfoService;
import com.blockchain.service.UserService;
import com.google.code.kaptcha.Producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.blockchain.common.lang.Const.ip;
import static com.blockchain.common.lang.Const.port;
import static com.blockchain.utils.RandomStringUtil.getRandString;

@Slf4j
@RestController
public class AuthController extends BaseController{

	@Autowired
	ProjectinfoService projectinfoService;
	@Autowired
	BlockprojectinfoService blockprojectinfoService;
	@Autowired
	Producer producer;

	@Autowired
	UserService userService;

	@GetMapping("/captcha")
	public Result captcha() throws IOException {
		//log.info("code");
		String key = UUID.randomUUID().toString();
		String code = producer.createText();

		// 为了测试
		//key = "aaaaa";
		//code = "25347";

		BufferedImage image = producer.createImage(code);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		//log.info("code:-----"+code);


		BASE64Encoder encoder = new BASE64Encoder();
		String str = "data:image/jpeg;base64,";

		String base64Img = str + encoder.encode(outputStream.toByteArray());

		//redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);

		return Result.succ(
				MapUtil.builder()
						.put("token", key)
						.put("captchaImg", base64Img)
						.put("code",code)
						.build()

		);
	}

	@PostMapping("/register")
	public Result register(@RequestBody User user) throws IOException {
		log.info("执行---------------------------------------------");
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(User::getPersonId,user.getPersonId());
		User userServiceOne = userService.getOne(queryWrapper);

		LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper();
		queryWrapper1.eq(User::getUsername,user.getUsername());
		User userServiceOne1 = userService.getOne(queryWrapper1);

		String userid=getRandString(6);
		user.setUserId(userid);
		user.setTotalmoney("0");
		if(userServiceOne == null && userServiceOne1==null){
			userService.save(user);
			return Result.succ(
					MapUtil.builder()
							.put("object", user)
							.build()

			);
		}else{
			return Result.fail("该身份证或用户名已注册");

		}
	}

	@PostMapping("/updateuser")
	public Result updateuser(@RequestBody User user) throws IOException {
		log.info("执行updateuser---------------------------------------------");
		UpdateWrapper<User> userUpdateWrapper =  new UpdateWrapper<>();
		userUpdateWrapper.eq("person_id",user.getPersonId());
		userUpdateWrapper.set("password",user.getPassword());
		//userUpdateWrapper.eq("person_id",user.getPersonId());
		userUpdateWrapper.set("username",user.getUsername());
		userUpdateWrapper.set("email",user.getEmail());
		userUpdateWrapper.set("phone",user.getPhone());
		userService.update(userUpdateWrapper);
		return Result.succ(
				MapUtil.builder()
						.put("object", user)
						.build()
		);
	}

	@PostMapping("/login")
	public Result login(@RequestBody User user) throws IOException {
		log.info("执行---------------------------------------------");
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword());
		User userServiceOne = userService.getOne(queryWrapper);
		if(userServiceOne == null){
			return Result.fail("s"
			);
		}else{
			return Result.succ(
					MapUtil.builder()
							.put("object", userServiceOne)
							.build()

			);
		}
	}
	//@CrossOrigin(allowCredentials = "true")
	@RequestMapping(value = "uploadfiles/upload")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam(value="file",required=false) MultipartFile file) {
		// 判断文件是否存在
		if (file.isEmpty()) {
			return error("文件不能为空");
		}
		// 判断文件类型是否为图片
		String contentType = file.getContentType();
		if (!contentType.startsWith("image/")) {

			return error("文件类型不正确");
		}
		try {
			//System.out.println("11111111111111111222222");
			String originalFilename=file.getOriginalFilename();
			String flag= IdUtil.fastSimpleUUID();
			// 生成唯一的文件名
			//String fileName = UUID.randomUUID().toString() + "." + getFileExtension(file.getOriginalFilename());
			// 保存图片
			//byte[] bytes = file.getBytes();
			String rootFilePath=System.getProperty("user.dir")+"/src/main/resources/static/images/"+flag+"_"+originalFilename;
			FileUtil.writeBytes(file.getBytes(),rootFilePath);
			String url=ip+":"+port+"/files/"+flag;
//			Path path = Paths.get(Const.uploadimage + fileName);
//			Files.write(path, bytes);
			// 返回成功结果
			Map<String, Object> result = new HashMap<>();
			result.put("errno", 0);
			Map<String, String> data = new HashMap<>();
			data.put("url", url);
			data.put("alt", "图片描述文字");
			data.put("href", "图片的链接");
			result.put("data", data);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return error("保存文件失败");
		}
	}
	@CrossOrigin
	@GetMapping("files/{flag}")
	public void getFiles(@PathVariable String flag, HttpServletResponse response){
		OutputStream os;
		//String basePath=System.getProperty("user.dir")+"/src/main/resources/static/images/";
		String basePath="/opt/javaproject/src/main/resources/static/images/";
		List<String> fileNames=FileUtil.listFileNames(basePath);
		String filename =fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
		//System.out.println(basePath);
		try{
			if(StrUtil.isNotEmpty(filename)){
				response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
				response.setContentType("application/octet-stream");
				byte[] bytes=FileUtil.readBytes(basePath+filename);
				os=response.getOutputStream();
				os.write(bytes);
				os.flush();
				os.close();
			}
		}catch (Exception e){
			System.out.println("文件下载失败");
		}
	}


	@PostMapping("/uploadHtml")
	public Result uploadHtml(@RequestBody Blockprojectinfo object) throws IOException {
		log.info("执行---------------------------------------------");
		String projectid=getRandString(6);

		String userid=object.getUserid();
		String expetationamount=object.getExpetationamount();
		String projectname=object.getProjectname();
		String username=object.getUsername(); //username这里是真是姓名
		String context=object.getProjectinfo();

		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(User::getName,username).eq(User::getUserId,userid);
		User userServiceOne = userService.getOne(queryWrapper);
		if(userServiceOne == null){
			return Result.fail("s"
			);
		}

		String info=object.getInfo(); //摘要
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String createtime= formatter.format(date);

		//存在本地数据库
		Projectinfo projectinfo=new Projectinfo();
		projectinfo.setTitle(projectname);
		projectinfo.setExpetationamount(expetationamount);
		projectinfo.setContext(context);
		projectinfo.setProjectid(projectid);
		projectinfo.setInfo(info);
		projectinfo.setUrl(object.getUrl());
		projectinfo.setDonationamount("0");
		projectinfo.setDonationtimes(0);
		projectinfoService.save(projectinfo);

		//存在区块链
		Blockprojectinfo blockprojectinfo=new Blockprojectinfo();
		blockprojectinfo.setProjectid(projectid);
		blockprojectinfo.setProjectname(projectname);
		blockprojectinfo.setUserid(userid);
		blockprojectinfo.setUsername(username);
		blockprojectinfo.setExpetationamount(expetationamount);
		blockprojectinfo.setProjectinfo(info);//摘要
		blockprojectinfo.setCreatetime(createtime);
		blockprojectinfo.setDonationtimes("0");
		blockprojectinfo.setDonationamount("0");
		if(blockprojectinfoService.save(blockprojectinfo) ) {
			System.out.println("cg");
		}else {
			System.out.println("sb");
		}
//
//		log.info(userid);
//		log.info(expetationamount);
		return Result.succ(
				MapUtil.builder()
						.put("projectid",projectid )
						.put("object",object )
						.build()
		);
	}

	@GetMapping("getHtml")
	public Object getHtml(String id){
		System.out.println(id);
		LambdaQueryWrapper<Projectinfo> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(Projectinfo::getProjectid,id);


		Projectinfo projectinfoServiceOne=projectinfoService.getOne(queryWrapper);
		if(projectinfoServiceOne == null){
			return Result.fail("fail"
			);
		}else{
			return Result.succ(
					MapUtil.builder()
							.put("object", projectinfoServiceOne)
							.build()

			);
		}
	}

	private Map<String, Object> error(String message) {
		Map<String, Object> result = new HashMap<>();
		result.put("errno", 1);
		result.put("message", message);
		return result;
	}
	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex < 0) {
			return "";
		}
		return fileName.substring(dotIndex + 1);
	}


	/**
	 * 获取用户信息接口
	 * @param principal
	 * @return
	 */
//	@GetMapping("/sys/menu/nav")
//	public Result menu(Principal principal) {
//		List<NavItem> nav = new ArrayList<>();
//		// 构造菜单数据
//		NavItem nav1=new NavItem();
//		nav1.setComponent("");
//		nav1.setName("SysManga");
//		nav1.setIcon("el-icon-s-operation");
//		nav1.setTitle("数据查询");
//		nav1.setPath("");
//
//		NavItem nav2=new NavItem();
//		nav2.setComponent("sys/User");
//		nav2.setName("SysUser");
//		nav2.setIcon("el-icon-s-custom");
//		nav2.setTitle("用户捐款记录查询");
//		nav2.setPath("/sys/users");
//
//		NavItem nav3=new NavItem();
//		nav3.setComponent("sys/Project");
//		nav3.setName("projectInfo");
//		nav3.setIcon("el-icon-rank");
//		nav3.setTitle("项目使用记录查询");
//		nav3.setPath("/sys/project");
//
//		NavItem nav4=new NavItem();
//		nav4.setComponent("sys/latestproject");
//		nav4.setName("SysMenu");
//		nav4.setIcon("el-icon-menu");
//		nav4.setTitle("查询最新项目信息");
//		nav4.setPath("/sys/latestproject");
//
//		nav.add(nav2);
//		nav.add(nav3);
//		nav.add(nav4);
//		nav1.setChildren(nav);
//
//		return Result.succ(MapUtil.builder()
//				.put("nav", nav1)
//
//				.map()
//		);
//	}


}
