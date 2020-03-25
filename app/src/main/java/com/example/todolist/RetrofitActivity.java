package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist.Retrofit.APIUtils;
import com.example.todolist.Retrofit.Data;
import com.example.todolist.Retrofit.Employee;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    ListView lsv;
    ArrayList<Employee> employeesList;
    ArrayList employeesListContent;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        lsv = findViewById(R.id.lsvData);
        employeesList = new ArrayList();
        employeesListContent = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, employeesListContent);
        lsv.setAdapter(adapter);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        getEmployee();
    //  createEmployee();
     // updateEmployee();
      // deleteEmployee();
    }

    private void deleteEmployee() {
        Data data = APIUtils.getData();
       Call<Employee> call = data.deleteEmployee(15);
       call.enqueue(new Callback<Employee>() {
           @Override
           public void onResponse(Call<Employee> call, Response<Employee> response) {

               String content = " ";
               content += "Code : "  + response.code();

               employeesListContent.add(content);
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onFailure(Call<Employee> call, Throwable t) {

           }
       });

    }

    private void updateEmployee() {
        Employee employee  = new Employee(12,"no title","no text");
        Data data = APIUtils.getData();
        Call<Employee> callBack  = data.updateEmployee(2, employee);
        callBack.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                Employee employeeUpdate =  response.body();
                String content = " ";
                content += "Code : " + response.code() + "\n";
                content += "ID :" + employeeUpdate.getId() + "\n";
                content += "User ID :" + employeeUpdate.getUserId() + "\n";
                content += "Title :" + employeeUpdate.getTitle() + "\n";
                content += "Text :" + employeeUpdate.getBody() + "\n";
                employeesListContent.add(content);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.i("aaa", "Load fail: " + t.getMessage());
                Toast.makeText(RetrofitActivity.this, "Load fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createEmployee() {
        Employee employee = new Employee(24, "New title", "new body");
        Data data = APIUtils.getData();
        Call<Employee> calBack = data.saveEmployee(25,"new titele","new text ");
        calBack.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                Employee employeeCreate =  response.body();
                String content = " ";
                content += "Code : " + response.code() + "\n";
                content += "ID :" + employeeCreate.getId() + "\n";
                content += "User ID :" + employeeCreate.getUserId() + "\n";
                content += "Title :" + employeeCreate.getTitle() + "\n";
                content += "Text :" + employeeCreate.getBody() + "\n\n";
                employeesListContent.add(content);
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.i("aaa", "Load fail: " + t.getMessage());
                Toast.makeText(RetrofitActivity.this, "Load fail", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void getEmployee(){
        Data data = APIUtils.getData();
        Call<List<Employee>> callback = data.getAll();
        callback.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                List<Employee> employees = response.body();
                employeesList.addAll(employees);
                for (Employee employee : employeesList) {
                    String content = "";
                    content += "ID :" + employee.getId() + "\n";
                    content += "User ID :" + employee.getUserId() + "\n";
                    content += "Title :" + employee.getTitle() + "\n";
                    content += "Text :" + employee.getBody() + "\n";
                    employeesListContent.add(content);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.i("aaa", "Load fail: " + t.getMessage());
                Toast.makeText(RetrofitActivity.this, "Load fail", Toast.LENGTH_SHORT).show();
            }

        });
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(RetrofitActivity.this,ChiTietRetrofitActivity.class);
                intent.putExtra("congdeptrai", employeesList.get(position));
                startActivity(intent);
            }
        });
    }
    //    private void chiTietRitrofit(){
//        Intent intent  = new Intent(RetrofitActivity.this,ChiTietRetrofitActivity.class);
    //      intent.putExtra(ChiTietRetrofitActivity.KEY, employeesList.get(position));
//        startActivity(intent);
//    }
}


