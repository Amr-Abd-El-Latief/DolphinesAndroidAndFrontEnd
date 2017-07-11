package net.hopesun.dolphins;

import android.util.Log;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.File;

/**
 * Created by ahmedabd-elbaky on 5/12/17.
 */

public class Watson {
    public VisualClassification sendImage(String filePath){
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("ee3756739796104373c135d73aba476a9dc21d2d");

        System.out.println("Classify an image");
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(new File(filePath))
                .build();
        VisualClassification result = service.classify(options).execute();
        Log.d("RESULT", result.toString());
        return result;
    }

    public VisualClassification sendImage(String name, byte[] imageBinary){
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("ee3756739796104373c135d73aba476a9dc21d2d");

        System.out.println("Classify an image");
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(imageBinary, name)
                .build();
        VisualClassification result = service.classify(options).execute();
        Log.d("RESULT", result.toString());
        return result;
    }
}
