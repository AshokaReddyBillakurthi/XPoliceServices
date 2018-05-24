package com.xpoliceservices.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xpoliceservices.app.BaseActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.TermsAndConditionsActivity;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.custom.CustomDialog;
import com.xpoliceservices.app.database.AppDataHelper;
import com.xpoliceservices.app.database.EndUserDataHelper;
import com.xpoliceservices.app.model.DataModel;
import com.xpoliceservices.app.model.EndUser;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.DialogUtils;
import com.xpoliceservices.app.utils.OkHttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EndUserRegistrationFragment extends BaseFragment {

    private TextView tvState, tvTermsandConditions,
            tvDistrict, tvSubDivisions, tvDivisionPoliceStation;
    private EditText edtFirstName, edtLastName, edtMobileNumber,
            edtEmail, edtPassword;
    private CheckBox cbxTermsAndConditions;
    private LinearLayout llRegister;
    private ImageView ivCamera, ivUserImg;
    private static final int CAMERA_CAPTURE = 1;
    public static final String TAG = EndUserRegistrationFragment.class.getSimpleName();
    private CustomDialog customDialog;
    private List<DataModel.Division> divisionPoliceStationList;
    private List<DataModel.SubDivision> subDivisionList;
    private List<DataModel.District> districtList;
    private List<DataModel.State> stateList;
    private String stateCode = "";
    private String districtCode = "";
    private String subDivisionCode = "";
    private boolean isPosted = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enduser_registration,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTermsandConditions = view.findViewById(R.id.tvTermsandConditions);
        llRegister = view.findViewById(R.id.llRegister);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtMobileNumber = view.findViewById(R.id.edtMobileNumber);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        tvState = view.findViewById(R.id.tvState);
        tvDistrict = view.findViewById(R.id.tvDistrict);
        tvSubDivisions = view.findViewById(R.id.tvSubDivisions);
        tvDivisionPoliceStation = view.findViewById(R.id.tvDivisionPoliceStation);
        ivCamera = view.findViewById(R.id.ivCamera);
        ivUserImg = view.findViewById(R.id.ivUserImg);
        cbxTermsAndConditions = view.findViewById(R.id.cbxTermsAndConditions);

        tvTermsandConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsAndConditionsActivity.class);
                startActivity(intent);
            }
        });

        tvDivisionPoliceStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subDivision = tvSubDivisions.getText().toString();
                if(TextUtils.isEmpty(subDivision)){
                    ((BaseActivity)getContext()).showToast("Please select sub division");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            divisionPoliceStationList =  AppDataHelper.
                                    getAllDivisionPoliceStationByDistrictCode(getContext(),
                                            subDivisionCode);
                            ((BaseActivity)getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customDialog = new CustomDialog(getContext(),
                                            divisionPoliceStationList,"Select Division/Police Station",
                                            true, false, true,
                                            new CustomDialog.OnDivisionPoliceStation() {
                                                @Override
                                                public void onDivisionPoliceStation(String divisionPoliceStation) {
                                                    if(null!=divisionPoliceStation){
                                                        tvDivisionPoliceStation.setText(divisionPoliceStation+"");
//                                                        divisionPoliceStationCode = divisionPoliceStation.getDivisionCode();
                                                    }
                                                    customDialog.dismiss();
                                                }
                                            });
                                    customDialog.show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        tvSubDivisions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String district = tvDistrict.getText().toString();
                if(TextUtils.isEmpty(district)){
                    ((BaseActivity)getContext()).showToast("Please select district first");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            subDivisionList = AppDataHelper.
                                    getAllSubDivisionsByDistrictCode(getContext(),districtCode);
                            ((BaseActivity)getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(null!=subDivisionList&&!subDivisionList.isEmpty()){
                                        customDialog = new CustomDialog(getContext(),
                                                subDivisionList, true,"Select Sub Division",
                                                false, false,
                                                new CustomDialog.OnSubDivisionSelected() {
                                                    @Override
                                                    public void OnSubDivisionSelected(DataModel.SubDivision subDivision) {
                                                        if(null!=subDivision){
                                                            tvSubDivisions.setText(subDivision.getSubDivisionName()+"");
                                                            subDivisionCode = subDivision.getSubDivisionCode();
                                                        }
                                                        customDialog.dismiss();
                                                    }
                                                });
                                        customDialog.show();
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        tvDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = tvState.getText().toString();
                if(TextUtils.isEmpty(state)){
                    ((BaseActivity)getContext()).showToast("Please select state first");
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            districtList = AppDataHelper.getAllDistrictByStateCode(getContext(),
                                    stateCode);
                            ((BaseActivity)getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(null!=districtList&&!districtList.isEmpty()){
                                        customDialog = new CustomDialog(getContext(),
                                                districtList, "Select District", true,
                                                false, true,
                                                new CustomDialog.OnDistrictSelected() {
                                                    @Override
                                                    public void onDistrictSelected(DataModel.District district) {
                                                        if(null!=district){
                                                            tvDistrict.setText(district.getDistrictName()+"");
                                                            districtCode = district.getDistrictCode();
                                                        }
                                                        customDialog.dismiss();
                                                    }
                                                });
                                        customDialog.show();
                                    }
                                }
                            });
                        }
                    }).start();
                }

            }
        });

        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        stateList = AppDataHelper.getAllStates(getContext());
                        ((BaseActivity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(null!=stateList&&!stateList.isEmpty()){
                                    customDialog = new CustomDialog(getContext(),
                                            true, stateList ,"Select State",
                                            true,false,
                                            new CustomDialog.OnStateSelected() {
                                                @Override
                                                public void onStateSelected(DataModel.State state) {
                                                    if(null!=state){
                                                        stateCode = state.getStateCode();
                                                        tvState.setText(state.getStateName()+"");
                                                    }
                                                    customDialog.dismiss();
                                                }
                                            });
                                    customDialog.show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        llRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEndUser();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    private void createEndUser() {
        try {
            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String mobileNo = edtMobileNumber.getText().toString().trim();
            String state = tvState.getText().toString().trim();
            String district = tvDistrict.getText().toString().trim();
            String subDivision = tvSubDivisions.getText().toString().trim();
            String divisionPoliceStation = tvDivisionPoliceStation.getText().toString().trim();
            if (validateData(firstName, lastName, email, password,
                    mobileNo, state, district, subDivision,divisionPoliceStation)) {
                ArrayList<EndUser> arrayList = new ArrayList<>();
                EndUser user = new EndUser();
                user.firstName = firstName;
                user.lastName = lastName;
                user.email = email;
                user.password = password;
                user.mobileNumber = mobileNo;
                user.state = state;
                user.city = "";
                user.area = "";
                user.image = ((BaseActivity)getContext()).userImg;
                user.userType = ((BaseActivity)getContext()).userType;
                user.district = district;
                user.subDivision = subDivision;
                user.divisionPoliceStation = divisionPoliceStation;
                postDataToServer(user);
                if(isPosted){
                    arrayList.add(user);
                    new UserAsyncTask().execute(arrayList);
                    ((BaseActivity)getContext()).showToast("Data posted Successfully");
                }
                else{
                    ((BaseActivity)getContext()).showToast("Failed");
                }
//                arrayList.add(user);
//                new UserAsyncTask().execute(arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UserAsyncTask extends AsyncTask<ArrayList<EndUser>, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(ArrayList<EndUser>[] arrayLists) {
            EndUserDataHelper.insertUserData(getContext(),arrayLists[0]);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            DialogUtils.showDialog(getContext(), "User Registered Successfully",
                    AppConstents.FINISH, false);
        }
    }

    private boolean validateData(String firstName, String lastName,
                                 String email, String password, String mobileNo,
                                 String state,String district, String subDivision,
                                 String divisionPoliceStation) {
        boolean isValid = true;
        if (TextUtils.isEmpty(firstName)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please enter first name");
        } else if (TextUtils.isEmpty(lastName)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please enter last name");
        } else if (TextUtils.isEmpty(mobileNo)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please enter mobile number");
        } else if (!(mobileNo.length() == 10)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please enter 10 digit mobile number");
        } else if (TextUtils.isEmpty(email)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please enter email");
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please enter valid email");
        } else if (TextUtils.isEmpty(password)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please enter password");
        } else if (TextUtils.isEmpty(state)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please select state");
        } else if (TextUtils.isEmpty(district)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please select district");
        } else if (TextUtils.isEmpty(subDivision)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please select sub division");
        } else if (TextUtils.isEmpty(divisionPoliceStation)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please select division with police station");
        } else if (TextUtils.isEmpty(((BaseActivity)getContext()).userImg)) {
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please capture Image");
        } else if(!cbxTermsAndConditions.isChecked()){
            isValid = false;
            ((BaseActivity)getContext()).showToast("Please accept the Terms and Conditions");
        }
        return isValid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ((BaseActivity)getContext()).storeImage(bitmap);
                ivUserImg.setImageBitmap(bitmap);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                ((BaseActivity)getContext()).showToast("User cancelled image capture");
            } else {
                ((BaseActivity)getContext()).showToast("Failed to capture image");
            }
        }
    }

    private void postDataToServer(EndUser user) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", user.firstName);
            jsonObject.put("lastName", user.lastName);
            jsonObject.put("email", user.email);
            jsonObject.put("password", user.password);
            jsonObject.put("mobileNumber", user.mobileNumber);
            jsonObject.put("isActive", true);
            jsonObject.put("state", user.state);
            jsonObject.put("city", "city");
            jsonObject.put("area", "area");
            jsonObject.put("image", user.image);
            jsonObject.put("userType", user.userType);
            jsonObject.put("district",user.district);
            jsonObject.put("subDivision",user.subDivision);
            jsonObject.put("divisionPoliceStation",user.divisionPoliceStation);
            String body = jsonObject.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL + ApiServiceConstants.USER_REGISTRATION)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .post(requestBody);
            Request request = builder.build();
            client.newCall(request).enqueue(new  Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ((BaseActivity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isPosted = false;
                            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    ((BaseActivity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (response.message().equalsIgnoreCase("OK")) {
                                    isPosted = true;
//                                    JSONObject jsonObj = new JSONObject(body);
//                                    String message = jsonObj.getString("msg");
//                                    DialogUtils.showDialog(UserRegistrationActivity.this,message.toString(),AppConstents.FINISH,false);
//                                    Toast.makeText(UserRegistrationActivity.this,message.toString(),Toast.LENGTH_LONG).show();
                                } else {
                                    isPosted = false;
//                                    Toast.makeText(UserRegistrationActivity.this,R.string.error_message,Toast.LENGTH_LONG).show();
                                    DialogUtils.showDialog(getContext(), getResources().getString(R.string.error_message), AppConstents.FINISH, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
