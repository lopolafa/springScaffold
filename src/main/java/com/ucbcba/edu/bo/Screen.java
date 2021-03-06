package com.ucbcba.edu.bo;

import org.w3c.dom.Attr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Screen {
    Scanner scanner;
    String input;
    Object object;
    public Screen()
    {
        scanner=new Scanner(System.in);
        object=new Object();
        object.directions=new Directions();
    }
    public void setup() throws IOException {
        System.out.println("Ingrese la direccion de la carpeta donde se encuentran los modelos");
        System.out.println("(De preferencia si se encuentran en el package principal ya que \n los archivos que se generaran se guardaran en esta misma ruta)");
        input=scanner.nextLine();
        object.directions.setPath(input);
        if (is_folder_valid())
        {
            System.out.println("Carpeta verificada");
            init();
            start();
        }
        else
        {
            System.out.println("Carpeta no encontrada o no es una carpeta, verifique");
        }
    }

    private void start() throws IOException {
        boolean state=true;
        while (state)
        {
            System.out.println("      ");
            System.out.println("Ingrese el nombre del modelo");
            input=scanner.nextLine();
            if (input.equals("-q"))
            {
                state=false;
            }
            else
            {
                object.directions.setup(input);
                scaffold();
            }
        }
    }

    private void scaffold() throws IOException {
        List<Attribute> attributes=get_attributes();
        object.attributes=attributes;
        if (Scaffold.scaffold(object))
        {
            System.out.println("Archivos creados exitosamente");
        }
        else
        {
            System.out.println("Algo paso, no se completaron las tareas");
        }
    }

    private List<Attribute> get_attributes() {
        boolean state=true;
        List<Attribute> attributes=new ArrayList<>();
        System.out.println("Ingrese en el siguiente formato los atributos");
        System.out.println("Al terminar ingrese --q para continuar el proceso");
        System.out.println("NAME_OF_PROP:TYPE_OF_DATA:(KEY):(AUTO, IDENTITY)");
        System.out.println("ejemplo:");
        System.out.println("    nombre:String:Key:Auto");
        System.out.println("    edad:int");
        System.out.println("    fecha_de_cumpleaños:Date");
        while(state)
        {
            input=scanner.nextLine();

            if(input.equals("--q"))
            {
                state=false;
            }
            else
            {
                attributes.add(read_attrib(input));
            }

        }

        return attributes;
    }

    private Attribute read_attrib(String input) {
        String[] data=input.split(":");
        if (data.length==3)
        {
            return new Attribute(data[1], data[0], data[2], "");
        }
        if (data.length==4)
        {
            return new Attribute(data[1], data[0], data[2], data[3]);
        }
        else {
            return new Attribute(data[1], data[0], "", "");
        }
    }

    private void init() {
        System.out.println("App iniciada, Puede detener todo el proceso ingresando (-)");
        System.out.println("Ingrese el nombre del package");
        input=scanner.nextLine();
        object.packg=input;
    }

    private boolean is_folder_valid() {
        File file = new File(object.directions.getPath());
        return file.exists()&&file.isDirectory();
    }
}
