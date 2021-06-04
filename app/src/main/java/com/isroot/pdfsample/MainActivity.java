package com.isroot.pdfsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.isroot.pdfsample.databinding.ActivityMainBinding;
import com.isroot.pdfsample.databinding.PdfFirstPageBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Log.d("isroot", Environment.getExternalStorageDirectory().getAbsolutePath());

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pdfFirstPage = inflater.inflate(R.layout.pdf_first_page, null);
//        PdfFirstPageBinding firstPageBinding = DataBindingUtil.setContentView(this, R.layout.pdf_first_page);
//        firstPageBinding.textView.setText("이건 텍스트뷰");
        binding.makePdf.setOnClickListener(view -> {
            PdfGenerator.getBuilder()
                    .setContext(getApplicationContext())
                    .fromViewSource()
                    .fromView(pdfFirstPage)
                    .setFileName("Test-PDF")
                    .setFolderName("PDF_test")
                    .openPDFafterGeneration(true)
                    .build(new PdfGeneratorListener() {
                        @Override
                        public void onFailure(FailureResponse failureResponse) {
                            super.onFailure(failureResponse);
                            Log.e("isroot", "onFailure : "+failureResponse.getErrorMessage());
                            failureResponse.getThrowable().printStackTrace();
                        }

                        @Override
                        public void onStartPDFGeneration() {
                            Log.d("isroot", "startPDFGeneration");
                        }

                        @Override
                        public void onFinishPDFGeneration() {
                            Log.d("isroot", "finishPDFGeneration");
                        }

                        @Override
                        public void showLog(String log) {
                            super.showLog(log);
                        }

                        @Override
                        public void onSuccess(SuccessResponse response) {
                            super.onSuccess(response);
                            Log.d("isroot", "successPDFGeneration : "+response.getPath());
                        }
                    });
        });
    }
}