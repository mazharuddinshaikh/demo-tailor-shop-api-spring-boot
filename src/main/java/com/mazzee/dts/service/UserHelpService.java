package com.mazzee.dts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.UserHelpDto;
import com.mazzee.dts.dto.UserHelpStepDto;

@Service
public class UserHelpService {

	public List<UserHelpDto> getAppGuideList(String baseUrl) {
		List<UserHelpDto> helpList = new ArrayList<>();
		UserHelpDto signinUserHelpDto = getSigninHelp(baseUrl);
		UserHelpDto signupUserHelpDto = getSignUpHelp(baseUrl);
		UserHelpDto addDressHelpDto = getAddDressHelp(baseUrl);
		UserHelpDto getDressListHelpDto = getDressListHelp(baseUrl);
		UserHelpDto getUpdateDressHelpDto = getUpDateDressHelp(baseUrl);
		UserHelpDto updateUserDressTypeHelpDto = getUpDateUserDressTypeHelp(baseUrl);
		UserHelpDto forgotPasswordHelpDto = getForgetPasswordHelp(baseUrl);
		UserHelpDto updatePasswordHelpDto = getUpdatePasswordHelp(baseUrl);
		UserHelpDto updateUserHelpDto = getUpdateUserHelp(baseUrl);
		UserHelpDto updateShopHelpDto = getUpdateShopHelp(baseUrl);
		helpList.add(signinUserHelpDto);
		helpList.add(signupUserHelpDto);
		helpList.add(addDressHelpDto);
		helpList.add(getDressListHelpDto);
		helpList.add(getUpdateDressHelpDto);
		helpList.add(updateUserDressTypeHelpDto);
		helpList.add(forgotPasswordHelpDto);
		helpList.add(updatePasswordHelpDto);
		helpList.add(updateUserHelpDto);
		helpList.add(updateShopHelpDto);
		return helpList;
	}

	private UserHelpDto getSigninHelp(String baseUrl) {
		UserHelpDto signinHelpDto = new UserHelpDto("LOGIN", "Login");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Enter Credentials.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click Signin.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step4.setStepsImageList(step4ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		signinHelpDto.setHelpStepsList(stepsList);
		return signinHelpDto;
	}

	private UserHelpDto getSignUpHelp(String baseUrl) {
		UserHelpDto signupHelpDto = new UserHelpDto("SIGNUP", "Signup");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click signup.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Enter Details.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_signup.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click Signup");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_signup.jpg");
		step4.setStepsImageList(step4ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		signupHelpDto.setHelpStepsList(stepsList);
		return signupHelpDto;
	}

	private UserHelpDto getAddDressHelp(String baseUrl) {
		UserHelpDto signupHelpDto = new UserHelpDto("ADD DRESS", "Add Dress");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Click Add Dress.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_list.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click Add Dress.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_add_dress.jpg");
		step4.setStepsImageList(step4ImageList);
		UserHelpStepDto step5 = new UserHelpStepDto();
		step5.setTitle("Click Add Dress.");
		List<String> step5ImageList = new ArrayList<>();
		step5ImageList.add(baseUrl + "/" + "dts-images/digitailors_add_dress.jpg");
		step5.setStepsImageList(step5ImageList);
//		add customer details
		UserHelpStepDto step6 = new UserHelpStepDto();
		step6.setTitle("Add Customer Details.");
		List<String> step6ImageList = new ArrayList<>();
		step6ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_detail.jpg");
		step6.setStepsImageList(step6ImageList);
//		add invoice detail
		UserHelpStepDto step7 = new UserHelpStepDto();
		step7.setTitle("Add Invoice Details.");
		List<String> step7ImageList = new ArrayList<>();
		step7ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_detail.jpg");
		step7.setStepsImageList(step7ImageList);
//		add dress detail
		UserHelpStepDto step8 = new UserHelpStepDto();
		step8.setTitle("Add Dress Details.");
		List<String> step8ImageList = new ArrayList<>();
		step8ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_detail.jpg");
		step8.setStepsImageList(step8ImageList);
//		Add dress images
		UserHelpStepDto step9 = new UserHelpStepDto();
		step9.setTitle("Add Dress Images.");
		List<String> step9ImageList = new ArrayList<>();
		step9ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_dress_images.jpg");
		step9.setStepsImageList(step9ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		stepsList.add(step5);
		stepsList.add(step6);
		stepsList.add(step7);
		stepsList.add(step8);
		stepsList.add(step9);
		signupHelpDto.setHelpStepsList(stepsList);
		return signupHelpDto;
	}

	private UserHelpDto getDressListHelp(String baseUrl) {
		UserHelpDto dressListHelpDto = new UserHelpDto("GET DRESS LIST", "Get Dress List");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Click Dresses.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_list.jpg");
		step3.setStepsImageList(step3ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		dressListHelpDto.setHelpStepsList(stepsList);
		return dressListHelpDto;
	}

	private UserHelpDto getUpDateDressHelp(String baseUrl) {
		UserHelpDto updateDressHelpDto = new UserHelpDto("UPDATE DRESS", "Update Dress");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Click Dresses.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_list.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click On Dress.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_list.jpg");
		step4.setStepsImageList(step4ImageList);
//		add customer details
		UserHelpStepDto step5 = new UserHelpStepDto();
		step5.setTitle("Update Customer Details.");
		List<String> step5ImageList = new ArrayList<>();
		step5ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_detail.jpg");
		step5.setStepsImageList(step5ImageList);
//		add invoice detail
		UserHelpStepDto step6 = new UserHelpStepDto();
		step6.setTitle("Update Invoice Details.");
		List<String> step6ImageList = new ArrayList<>();
		step6ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_detail.jpg");
		step6.setStepsImageList(step6ImageList);
//		add dress detail
		UserHelpStepDto step7 = new UserHelpStepDto();
		step7.setTitle("Update Dress Details.");
		List<String> step7ImageList = new ArrayList<>();
		step7ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_detail.jpg");
		step7.setStepsImageList(step7ImageList);
//		Add dress images
		UserHelpStepDto step8 = new UserHelpStepDto();
		step8.setTitle("Update Dress Images.");
		List<String> step8ImageList = new ArrayList<>();
		step8ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_dress_images.jpg");
		step8.setStepsImageList(step8ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		stepsList.add(step5);
		stepsList.add(step6);
		stepsList.add(step7);
		stepsList.add(step8);

		updateDressHelpDto.setHelpStepsList(stepsList);
		return updateDressHelpDto;
	}

	private UserHelpDto getUpDateUserDressTypeHelp(String baseUrl) {
		UserHelpDto updateDressHelpDto = new UserHelpDto("UPDATE USER DRESS TYPE", "Update User Dress Type");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Click My Account.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_my_account.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click On Update User Dress Type.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_my_account.jpg");
		step4.setStepsImageList(step4ImageList);
//		add customer details
		UserHelpStepDto step5 = new UserHelpStepDto();
		step5.setTitle("Update Price.");
		List<String> step5ImageList = new ArrayList<>();
		step5ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_user_dress_type.jpg");
		step5.setStepsImageList(step5ImageList);
//		add invoice detail
		UserHelpStepDto step6 = new UserHelpStepDto();
		step6.setTitle("Update.");
		List<String> step6ImageList = new ArrayList<>();
		step6ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_user_dress_type.jpg");
		step6.setStepsImageList(step6ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		stepsList.add(step5);
		stepsList.add(step6);
		updateDressHelpDto.setHelpStepsList(stepsList);
		return updateDressHelpDto;
	}

	private UserHelpDto getForgetPasswordHelp(String baseUrl) {
		UserHelpDto updateDressHelpDto = new UserHelpDto("FORGOT PASSWORD", "Forgot Password");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Click on Forgot password Please click here.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Enter Username or Email.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_forgot_password.jpg");
		step4.setStepsImageList(step4ImageList);
//		add customer details
		UserHelpStepDto step5 = new UserHelpStepDto();
		step5.setTitle("Click Update password.");
		List<String> step5ImageList = new ArrayList<>();
		step5ImageList.add(baseUrl + "/" + "dts-images/digitailors_forgot_password.jpg");
		step5.setStepsImageList(step5ImageList);
//		add invoice detail
		UserHelpStepDto step6 = new UserHelpStepDto();
		step6.setTitle("You will get the default passowrd onyour registered email.");
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		stepsList.add(step5);
		stepsList.add(step6);

		updateDressHelpDto.setHelpStepsList(stepsList);
		return updateDressHelpDto;
	}

	private UserHelpDto getUpdatePasswordHelp(String baseUrl) {
		UserHelpDto updateDressHelpDto = new UserHelpDto("UPDATE PASSWORD", "Update Password");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Enter Credentials.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click My Account.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_list.jpg");
		step4.setStepsImageList(step4ImageList);
//		add customer details
		UserHelpStepDto step5 = new UserHelpStepDto();
		step5.setTitle("Click Update password.");
		List<String> step5ImageList = new ArrayList<>();
		step5ImageList.add(baseUrl + "/" + "dts-images/digitailors_my_account.jpg");
		step5.setStepsImageList(step5ImageList);
//		add invoice detail
		UserHelpStepDto step6 = new UserHelpStepDto();
		step6.setTitle("Enter Old Password and New Password.");
		List<String> step6ImageList = new ArrayList<>();
		step6ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_password.jpg");
		step6.setStepsImageList(step6ImageList);
		UserHelpStepDto step7 = new UserHelpStepDto();
		step7.setTitle("Click on Update Password.");
		List<String> step7ImageList = new ArrayList<>();
		step7ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_password.jpg");
		step7.setStepsImageList(step7ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		stepsList.add(step5);
		stepsList.add(step6);
		stepsList.add(step7);
		updateDressHelpDto.setHelpStepsList(stepsList);
		return updateDressHelpDto;
	}

	private UserHelpDto getUpdateUserHelp(String baseUrl) {
		UserHelpDto updateDressHelpDto = new UserHelpDto("UPDATE USER", "Update User");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Enter Credentials.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click My Account.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_list.jpg");
		step4.setStepsImageList(step4ImageList);
//		add customer details
		UserHelpStepDto step5 = new UserHelpStepDto();
		step5.setTitle("Click Update User.");
		List<String> step5ImageList = new ArrayList<>();
		step5ImageList.add(baseUrl + "/" + "dts-images/digitailors_my_account.jpg");
		step5.setStepsImageList(step5ImageList);
//		add invoice detail
		UserHelpStepDto step6 = new UserHelpStepDto();
		step6.setTitle("Update details.");
		List<String> step6ImageList = new ArrayList<>();
		step6ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_user.jpg");
		step6.setStepsImageList(step6ImageList);
		UserHelpStepDto step7 = new UserHelpStepDto();
		step7.setTitle("Click on Update.");
		List<String> step7ImageList = new ArrayList<>();
		step7ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_user.jpg");
		step7.setStepsImageList(step7ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		stepsList.add(step5);
		stepsList.add(step6);
		stepsList.add(step7);
		updateDressHelpDto.setHelpStepsList(stepsList);
		return updateDressHelpDto;
	}

	private UserHelpDto getUpdateShopHelp(String baseUrl) {
		UserHelpDto updateDressHelpDto = new UserHelpDto("UPDATE SHOP DETAILS", "Update Shop Details");
		UserHelpStepDto step1 = new UserHelpStepDto();
		step1.setTitle("Start App.");
		List<String> step1ImageList = new ArrayList<>();
		step1ImageList.add(baseUrl + "/" + "dts-images/digitailors_start.jpg");
		step1.setStepsImageList(step1ImageList);
		UserHelpStepDto step2 = new UserHelpStepDto();
		step2.setTitle("Click Signin.");
		List<String> step2ImageList = new ArrayList<>();
		step2ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step2.setStepsImageList(step2ImageList);
		UserHelpStepDto step3 = new UserHelpStepDto();
		step3.setTitle("Enter Credentials.");
		List<String> step3ImageList = new ArrayList<>();
		step3ImageList.add(baseUrl + "/" + "dts-images/digitailors_signin.jpg");
		step3.setStepsImageList(step3ImageList);
		UserHelpStepDto step4 = new UserHelpStepDto();
		step4.setTitle("Click My Account.");
		List<String> step4ImageList = new ArrayList<>();
		step4ImageList.add(baseUrl + "/" + "dts-images/digitailors_dress_list.jpg");
		step4.setStepsImageList(step4ImageList);
//		add customer details
		UserHelpStepDto step5 = new UserHelpStepDto();
		step5.setTitle("Click Update Shop.");
		List<String> step5ImageList = new ArrayList<>();
		step5ImageList.add(baseUrl + "/" + "dts-images/digitailors_my_account.jpg");
		step5.setStepsImageList(step5ImageList);
//		add invoice detail
		UserHelpStepDto step6 = new UserHelpStepDto();
		step6.setTitle("Update details.");
		List<String> step6ImageList = new ArrayList<>();
		step6ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_shop.jpg");
		step6.setStepsImageList(step6ImageList);
		UserHelpStepDto step7 = new UserHelpStepDto();
		step7.setTitle("Click on Update.");
		List<String> step7ImageList = new ArrayList<>();
		step7ImageList.add(baseUrl + "/" + "dts-images/digitailors_update_shop.jpg");
		step7.setStepsImageList(step7ImageList);
		List<UserHelpStepDto> stepsList = new ArrayList<>();
		stepsList.add(step1);
		stepsList.add(step2);
		stepsList.add(step3);
		stepsList.add(step4);
		stepsList.add(step5);
		stepsList.add(step6);
		stepsList.add(step7);
		updateDressHelpDto.setHelpStepsList(stepsList);
		return updateDressHelpDto;
	}
}
