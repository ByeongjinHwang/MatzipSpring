package com.koreait.matzip.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.model.RestFile;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Service
public class RestService {

	@Autowired
	private RestMapper mapper;

	@Autowired
	private CommonMapper cMapper;

	List<RestDMI> selRestList(RestPARAM param) {
		return mapper.selRestList(param);
	}

	int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}

	List<CodeVO> selCategoryList() {
		CodeVO p = new CodeVO();
		p.setI_m(1); // 음식점 카테고리 코드 = 1

		return cMapper.selCodeList(p);
	}

	RestDMI selRest(RestPARAM param) {
		return mapper.selRest(param);
	}

	List<RestRecMenuVO> selRestRecMenus(RestPARAM param) {
		return mapper.selRestRecMenus(param);
	}
	
	public List<RestRecMenuVO> selRestMenus(RestPARAM param) {
		
		return mapper.selRestMenus(param);
	}


	@Transactional // 트랜잭션 검
	// 디폴트가 오토커밋인데, 그걸 끄고 하나하나 실행해줌, 그리고 에러가 터지면 롤백시킴.
	// try catch가 들어있다고 보면 됨
	public void delRestTran(RestPARAM param) {
		mapper.delRestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}

	int delRestRecMenu(RestPARAM param) {
		return mapper.delRestRecMenu(param);
	}

	int delRestMenu(RestPARAM param) {
		if(param.getMenu_pic() != null && "".equals(param.getMenu_pic())) {
			String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";
			
			if(FileUtils.delFile(path + param.getMenu_pic())) {
				return mapper.delRestMenu(param);
			} else {
				return Const.FAIL;
			}
		}
		
		return mapper.delRestMenu(param);
	}

	public int insRecMenus(MultipartHttpServletRequest mReq) {
		int i_user = SecurityUtils.getLoginUserPk(mReq.getSession());
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		
		
		if(_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}
		
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");

		String path = mReq.getServletContext().getRealPath("/resources/img/rest/" + i_rest + "/rec_menu/");

		List<RestRecMenuVO> list = new ArrayList();

		for (int i = 0; i < menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);

			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStrToInt(menuPriceArr[i]);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			vo.setI_rest(i_rest);

			MultipartFile mf = fileList.get(i);

			if (mf.isEmpty()) {
				continue;
			}

			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;

			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setMenu_pic(saveFileNm);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		for (RestRecMenuVO vo : list) {
			mapper.insRestRecMenu(vo);
		}

		return i_rest;

	}

	public int delRestRecMenu(RestPARAM param, String realPath) {
		// 파일 삭제
		List<RestRecMenuVO> list = mapper.selRestRecMenus(param);
		if (list.size() == 1) {
			RestRecMenuVO item = list.get(0);

			if (item.getMenu_pic() != null && !item.getMenu_pic().equals("")) { // 이미지 있음 > 삭제!!
				File file = new File(realPath + item.getMenu_pic());
				if (file.exists()) {
					if (file.delete()) {
						return mapper.delRestRecMenu(param);
					} else {
						return 0;
					}
				}
			}
		}

		return mapper.delRestRecMenu(param);
	}
	

	public int insMenus(@ModelAttribute RestFile param, int i_user, MultipartHttpServletRequest mReq) {
		int i_rest = param.getI_rest();
		
		if(_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}
		
		List<MultipartFile> fileList = param.getMenu_pic();
		
		String path = mReq.getServletContext().getRealPath("/resources/img/rest/" + i_rest + "/menu/");

		List<RestRecMenuVO> list = new ArrayList();
		
		for (int i = 0; i < fileList.size(); i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);
			
			MultipartFile mf = fileList.get(i);
			
			if (mf.isEmpty()) {
				continue;
			}
			
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;
			
			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setI_rest(i_rest);
				vo.setMenu_pic(saveFileNm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (RestRecMenuVO vo : list) {
			mapper.insMenus(vo);
		}
		
		return i_rest;

	}
	
	//장난질 못하게!! (내가 쓴 글이 아니면 글을 못올림)
	private boolean _authFail(int i_rest, int i_user) {
		RestPARAM param = new RestPARAM();
		param.setI_rest(i_rest);
		
		RestDMI dbResult = mapper.selRest(param);
		if(dbResult == null || dbResult.getI_user() != i_user) {
			return true;
		}
		
		return false;
	}
	
}
