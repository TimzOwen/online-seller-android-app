package com.owen.netmall.Model;


public class Users

     {
         private String Name, Phone, Password;

         public Users()
         {

         }


         public Users(String name, String phone, String password)
         {
             this.Name = name;
             this.Phone = phone;
             this.Password = password;
         }

         public String getName()
         {
             return Name;
         }

         public void setName(String name)
         {
             Name = name;
         }

         public String getPhone()
         {
             return Phone;
         }

         public void setPhone(String phone)
         {
             Phone = phone;
         }

         public String getPassword()
         {
             return Password;
         }

         public void setPassword(String password)
         {
             Password = password;
         }
     }
