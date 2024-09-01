package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsestsOkhttp {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyYUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyNTcyMDM2NSwiaWF0IjoxNzI1MTIwMzY1fQ.XRtwk7kphLc4XlVyBb4MHOVisF9GfOcxTJfK8GhjZJ0";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();


    @Test
    public void getAllContactsSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        GetAllContactsDTO contactsDTO = gson.fromJson(response.body().string(), GetAllContactsDTO.class);
        List<ContactDTO> contacts = contactsDTO.getContacts();
        for(ContactDTO c:contacts){
            System.out.println(c.getId());
            System.out.println(c.getEmail());
            System.out.println("============");
        }

    }

    @Test
    public void getAllContactsWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization", "sdkfhsdlfj")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 401);
    }
}
