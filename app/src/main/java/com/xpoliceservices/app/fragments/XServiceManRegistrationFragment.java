package com.xpoliceservices.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xpoliceservices.app.BaseActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.custom.CustomDialog;
import com.xpoliceservices.app.database.AppDataHelper;
import com.xpoliceservices.app.database.XServiceManDataHelper;
import com.xpoliceservices.app.model.DataModel;
import com.xpoliceservices.app.model.XServiceMan;
import com.xpoliceservices.app.utils.DialogUtils;
import com.xpoliceservices.app.utils.FilePathUtils;

import java.util.ArrayList;
import java.util.List;

public class XServiceManRegistrationFragment extends BaseFragment {

    private EditText edtFirstName, edtLastName, edtExPoliceId, edtMobileNumber, edtEmail, edtPassword;
    private TextView tvState, tvDistrict,tvSubDivisions,tvDivisionPoliceStation, tvTitle;
    private CheckBox cbxPolicePermissions, cbxPIdAddressTrace, cbxMatrimonialVerifications, cbxDraftingComplaints;
    private Button btnUploadDocs;
    private LinearLayout llDocuments, llRegister;
    private ImageView ivCamera, ivUserImg;
    private boolean isServiceSelectd;
    private ArrayList<String> docList;
    private List<DataModel.Division> divisionPoliceStationList;
    private List<DataModel.SubDivision> subDivisionList;
    private List<DataModel.District> districtList;
    private List<DataModel.State> stateList;
    private String stateCode = "";
    private String districtCode = "";
    private String subDivisionCode = "";
    private String selectedFilePath = "";
    private CustomDialog customDialog;
    private static final int PICK_FILE_REQUEST = 100;
    private static final int CAMERA_CAPTURE = 101;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xserviceman_registration,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtExPoliceId = view.findViewById(R.id.edtExPoliceId);
        edtMobileNumber = view.findViewById(R.id.edtMobileNumber);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        tvState = view.findViewById(R.id.tvState);
        tvDistrict = view.findViewById(R.id.tvDistrict);
        tvSubDivisions = view.findViewById(R.id.tvSubDivisions);
        tvDivisionPoliceStation = view.findViewById(R.id.tvDivisionPoliceStation);
        tvTitle = view.findViewById(R.id.tvTitle);
        btnUploadDocs = view.findViewById(R.id.btnUploadDocs);
        llDocuments = view.findViewById(R.id.llDocuments);
        llRegister = view.findViewById(R.id.llRegister);
        ivCamera = view.findViewById(R.id.ivCamera);
        ivUserImg = view.findViewById(R.id.ivUserImg);
        cbxPolicePermissions = view.findViewById(R.id.cbxPolicePermissions);
        cbxPIdAddressTrace = view.findViewById(R.id.cbxPIdAddressTrace);
        cbxMatrimonialVerifications = view.findViewById(R.id.cbxMatrimonialVerifications);
        cbxDraftingComplaints = view.findViewById(R.id.cbxDraftingComplaints);


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
                                            divisionPoliceStationList,"Select Sub Division",
                                            true, true, true,
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

        btnUploadDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        llRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerExServiceMan();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }


    private void registerExServiceMan() {
        try {
            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String exPoliceId = edtExPoliceId.getText().toString().trim();
            String mobileNo = edtMobileNumber.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String state = tvState.getText().toString().trim();
            String district = tvDistrict.getText().toString().trim();
            String subDivision = tvSubDivisions.getText().toString().trim();
            String divisionPoliceStation = tvDivisionPoliceStation.getText().toString().trim();
            StringBuilder strDoc = new StringBuilder();
            StringBuilder strServices = new StringBuilder();
            if (cbxPolicePermissions.isChecked()
                    || cbxDraftingComplaints.isChecked()
                    || cbxMatrimonialVerifications.isChecked()
                    || cbxPIdAddressTrace.isChecked()) {
                isServiceSelectd = true;

                if (cbxPolicePermissions.isChecked()) {
                    strServices.append(cbxPolicePermissions.getText().toString()).append(",");
                }

                if (cbxDraftingComplaints.isChecked()) {
                    strServices.append(cbxDraftingComplaints.getText().toString()).append(",");
                }

                if (cbxMatrimonialVerifications.isChecked()) {
                    strServices.append(cbxMatrimonialVerifications.getText().toString()).append(",");
                }

                if (cbxPIdAddressTrace.isChecked()) {
                    strServices.append(cbxPIdAddressTrace.getText().toString()).append(",");
                }
            }
            if (validateData(firstName, lastName, exPoliceId, mobileNo, email, password,
                    state, district, subDivision,divisionPoliceStation)) {
                ArrayList<XServiceMan> arrayList = new ArrayList<>();
                XServiceMan exServiceMan = new XServiceMan();
                exServiceMan.firstName = firstName;
                exServiceMan.lastName = lastName;
                exServiceMan.email = email;
                exServiceMan.password = password;
                exServiceMan.mobileNo = mobileNo;
                exServiceMan.state = state;
                exServiceMan.city = "";
                exServiceMan.area = "";
                exServiceMan.isActive = 1;
                exServiceMan.userType = ((BaseActivity)getContext()).userType;
                exServiceMan.userImg = ((BaseActivity)getContext()).userImg;
                exServiceMan.status = 0;
                if (docList != null && docList.size() > 0) {
                    for (String str : docList) {
                        strDoc.append(str).append(",");
                    }
                }
                exServiceMan.reqDocs = strDoc.toString();
                exServiceMan.services = strServices.toString();
                exServiceMan.district = district;
                exServiceMan.subDivision = subDivision;
                exServiceMan.circlePolicestation = divisionPoliceStation;
//                if(postDataToServer(exServiceMan)){
//                    showToast("Successfully Inserted");
//                }
//                else{
//                    showToast("Insertion Failed");
//                }
                arrayList.add(exServiceMan);
                new ExServiceManRegistrationAsyncTask().execute(arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateData(String firstName, String lastName, String exPoliceId, String mobNo,
                                 String email, String password,
                                 String state, String district, String subdivision,String divisionPoliceStation) {
        boolean isValid = true;

        if (TextUtils.isEmpty(firstName)) {
            ((BaseActivity)getContext()).showToast("Please enter first name");
            isValid = false;
        } else if (TextUtils.isEmpty(lastName)) {
            ((BaseActivity)getContext()).showToast("Please enter last name");
            isValid = false;
        } else if (TextUtils.isEmpty(exPoliceId)) {
            ((BaseActivity)getContext()).showToast("Please enter Ex. Police Id");
            isValid = false;
        } else if (TextUtils.isEmpty(mobNo)) {
            ((BaseActivity)getContext()).showToast("Please enter mobile number");
            isValid = false;
        } else if (mobNo.length() != 10) {
            ((BaseActivity)getContext()).showToast("Mobile number should be 10 digits");
            isValid = false;
        } else if (TextUtils.isEmpty(email)) {
            ((BaseActivity)getContext()).showToast("Please enter email");
            isValid = false;
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            ((BaseActivity)getContext()).showToast("Please enter valid email id");
            isValid = false;
        } else if (TextUtils.isEmpty(password)) {
            ((BaseActivity)getContext()).showToast("Please enter password");
            isValid = false;
        } else if (TextUtils.isEmpty(state)) {
            ((BaseActivity)getContext()).showToast("Please select state");
            isValid = false;
        } else if (TextUtils.isEmpty(district)) {
            ((BaseActivity)getContext()).showToast("Please select District");
            isValid = false;
        } else if (TextUtils.isEmpty(subdivision)) {
            ((BaseActivity)getContext()).showToast("Please select Sub Division");
            isValid = false;
        } else if(TextUtils.isEmpty(divisionPoliceStation)){
            ((BaseActivity)getContext()).showToast("Please select Division with PoliceStation");
            isValid = false;
        } else if (!isServiceSelectd) {
            ((BaseActivity)getContext()).showToast("Please select atleast one service");
            isValid = false;
        }
        return isValid;
    }

    class ExServiceManRegistrationAsyncTask extends AsyncTask<ArrayList<XServiceMan>, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(ArrayList<XServiceMan>[] arrayLists) {
            XServiceManDataHelper.insertXserviceManData(getContext(),arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            DialogUtils.showDialog(getContext(), "Service Man Registered Successfully",
                    AppConstents.FINISH, false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST&&data!=null) {
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePathUtils.getFilePathByUriString(getContext(), selectedFileUri);
                Log.i("XserviceMan Reg", "Selected File Path:" + selectedFilePath);

                if (!TextUtils.isEmpty(selectedFilePath)) {
                    final View view = LayoutInflater.from(getContext())
                            .inflate(R.layout.layout_documents, null);
                    final TextView tvUploadFineName = view.findViewById(R.id.tvUploadedFile);
                    ImageView ivRemove = view.findViewById(R.id.ivRemove);
                    tvUploadFineName.setText(selectedFilePath);
                    docList.add(selectedFilePath);
                    llDocuments.addView(view);
                    ivRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = tvUploadFineName.getText().toString();
                            llDocuments.removeView(view);
                            docList.remove(text);
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == CAMERA_CAPTURE) {
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
    }

}
