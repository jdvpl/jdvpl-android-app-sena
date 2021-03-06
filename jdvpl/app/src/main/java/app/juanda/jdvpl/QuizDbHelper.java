package app.juanda.jdvpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                QuizContract.CategoriesTable.TABLE_NAME + "( " +
                QuizContract.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                QuizContract.CategoriesTable.TABLE_NAME + "(" + QuizContract.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("HTML5");
        insertCategory(c1);
        Category c2 = new Category("CSS3");
        insertCategory(c2);
        Category c3 = new Category("BOOTSTRAP");
        insertCategory(c3);
        Category c4 = new Category("MYSQL");
        insertCategory(c4);
        Category c5 = new Category("PHP");
        insertCategory(c5);

    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for (Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(QuizContract.CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("??Cu??l de los siguientes Doctype es el que utilizar??as para un documento HTML5?",
                "A:"+" "+ "<!doctype html>", "B:"+" "+ " <!doctype html5>", "C: "+" "+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 5.0 Transitional//EN\"> ", 1,
                Question.DIFFICULTY_FACIL, Category.HTML5);
        insertQuestion(q1);
        Question q2 = new Question("??Qu?? entiende por HTML?",
                "A:"+"  "+ " HyperTextMaskLanguage", "B:"+"  "+ " HardTextMarkupLanguage", "C: "+" "+ " HyperTextMarkupLanguage ", 3,
                Question.DIFFICULTY_FACIL, Category.HTML5);
        insertQuestion(q2);
        Question q3 = new Question("??Qu?? etiqueta utilizamos para definir el cuerpo del documento?",
                "A:"+" "+ " background", "B:"+" "+ " body", "C: "+" "+ " html ", 2,
                Question.DIFFICULTY_FACIL, Category.HTML5);
        insertQuestion(q3);
        Question q4 = new Question("Selecciona el elemento inv??lido:",
                "A:"+" "+ "<meter>", "B:"+" "+ "<hgroup>", "C: "+" "+ "<post> ", 3,
                Question.DIFFICULTY_FACIL, Category.HTML5);
        insertQuestion(q4);
        Question q5 = new Question("Elija la etiqueta que nos da el t??tulo m??s grande",
                "A:"+" "+ " h1", "B:"+" "+ " h6", "C: "+" "+ " head", 1,
                Question.DIFFICULTY_FACIL, Category.HTML5);
        insertQuestion(q5);
        Question q6 = new Question("Elija la forma correcta de ingresar una imagen.",
                "A:"+" "+ "img src=\"foto.jpg\"", "B:"+" "+ "imagen src=\"foto.jpg\"", "C: "+" "+ "imghref=\"foto.jpg ", 1,
                Question.DIFFICULTY_MEDIO, Category.HTML5);
        insertQuestion(q6);
        Question q7 = new Question("??Qu?? etiquetas pueden figurar en la secci??n <head>?",
                "A:"+" "+ "link, meta, p", "B:"+" "+ " link, meta, header", "C: "+" "+ "link, meta, title", 3,
                Question.DIFFICULTY_MEDIO, Category.HTML5);
        insertQuestion(q7);
        Question q8 = new Question("??C??mo definimos un texto alternativo para una imagen?",
                "A:"+" "+ "imgsrc=\"foto.jpg\" alt=\"texto alternativo\"", "B:"+" "+ " imgsrc=\"foto.jpg\" value=\"texto alternativo\"", "C: "+" "+ " imgsrc=\"foto.jpg\" text=\"texto alternativo\"", 1,
                Question.DIFFICULTY_MEDIO, Category.HTML5);
        insertQuestion(q8);
        Question q9 = new Question("??C??mo hacemos para abrir un v??nculo en otra ventana?",
                "A:"+" "+ "href=\"www.algunsitio.com\" target=\"_new\"", "B:"+" "+ " href=\"www.algunsitio.com\" target=\"_blank\"", "C: "+" "+ " href=\"www.algunsitio.com\" new", 2,
                Question.DIFFICULTY_MEDIO, Category.HTML5);
        insertQuestion(q9);
        Question q10 = new Question("   ??Qu?? etiqueta define un salto de l??nea?",
                "A:"+" "+ " break", "B:"+" "+ " linebreak", "C: "+" "+ " br", 3,
                Question.DIFFICULTY_MEDIO, Category.HTML5);
        insertQuestion(q10);
        Question q11 = new Question("??Cu??l de los siguientes usos del elemento <script> te parece el m??s apropiado?",
                "A:"+" "+ " <script src=\"archivo.js\"></script>", "B:"+" "+ " <script type=\"text/javascript\" src=\"archivo.js\"></script>", "C: "+" <script src=\"archivo.js\" />", 2,
                Question.DIFFICULTY_DIFICIL, Category.HTML5);
        insertQuestion(q11);
        Question q12 = new Question("Si tuvieras que crear un campo de b??squeda, ??qu?? ser??a lo m??s apropiado?",
                "A:"+" "+ " <input type=\"find\" />", "B:"+" "+ " <input type=\"search\" />", "C: "+" <input type=\"text\" search />", 2,
                Question.DIFFICULTY_DIFICIL, Category.HTML5);
        insertQuestion(q12);
        Question q13 = new Question(" ??Cu??l de los siguientes scripts se utiliza generalmente para detectar capacidades de JS, HTML5 y CSS3 en el navegador?",
                "A:"+" "+ " HTML5 Shim", "B:"+" "+ " Modernizr", "C: "+"Google Detector", 2,
                Question.DIFFICULTY_DIFICIL, Category.HTML5);
        insertQuestion(q13);
        Question q14 = new Question(" ??Cu??l de los siguientes atributos permite mostrar un valor por defecto en un elemento input, pero que se borra cu??ndo hacemos foco en el mismo?",
                "A:"+" "+ " placeholder", "B:"+" "+ " value", "C: "+"  source", 1,
                Question.DIFFICULTY_DIFICIL, Category.HTML5);
        insertQuestion(q14);
        Question q15 = new Question(" Si estuvieras maquetando un blog, en cu??l de los siguientes elementos pondr??as los links a archivo, categor??as, ??conos de redes sociales, links a art??culos m??s populares, etc?",
                "A:"+" "+ " <aside>", "B:"+" "+ " <section>", "C: "+" <summary>", 1,
                Question.DIFFICULTY_DIFICIL, Category.HTML5);
        insertQuestion(q15);
        Question q16 = new Question("??C??mo se cambia el color del texto de un elemento en CSS?",
                "A:"+" "+ "textcolor ", "B:"+" "+ " text-color ", "C: "+" color ", 3,
                Question.DIFFICULTY_FACIL, Category.CSS3);
        insertQuestion(q16);
        Question q17 = new Question("??Qu?? propiedad de CSS se emplea para indicar que un texto se debe mostrar en cursiva (it??lica)?",
                "A:"+" "+ "font-italic  ", "B:"+" "+ " font-style  ", "C: "+"  Las anteriores respuestas no son correctas  ", 3,
                Question.DIFFICULTY_FACIL, Category.CSS3);
        insertQuestion(q17);
        Question q18 = new Question("??Cu??l es la sintaxis correcta en CSS?",
                "A:"+" "+ "body {color=black} ", "B:"+" "+ " body {color:black}  ", "C: "+" {body=color:black}  ", 2,
                Question.DIFFICULTY_FACIL, Category.CSS3);
        insertQuestion(q18);
        Question q19 = new Question("??Qu?? etiqueta de HTML se emplea para definir una hoja de estilo interna?",
                "A:"+" "+ "<style>", "B:"+" "+ " <link> ", "C: "+" <css> ", 1,
                Question.DIFFICULTY_FACIL, Category.CSS3);
        insertQuestion(q19);
        Question q20 = new Question("??Qu?? propiedad de CSS se emplea para cambiar el tipo de letra de un elemento?",
                "A:"+" "+ "font-family   ", "B:"+" "+ "text-family  ", "C: "+"font-type ", 1,
                Question.DIFFICULTY_FACIL, Category.CSS3);
        insertQuestion(q20);
        Question q21 = new Question("??Qu?? significa CSS?",
                "A:"+" "+ " Creative Style Sheets ", "B:"+" "+ "Cascading Style Sheets   ", "C: "+"Ninguna de las anteriores ", 2,
                Question.DIFFICULTY_MEDIO, Category.CSS3);
        insertQuestion(q21);
        Question q22 = new Question("Indica cu??l es la afirmaci??n incorrecta respecto a la definici??n de estilos CSS en una p??gina HTML",
                "A:"+" "+ " Se pueden definir dentro de la p??gina con el c??digo <style\n" +
                        "type=\"text/css\">...</style>  ", "B:"+" "+ "Se pueden definir dentro de la p??gina con el c??digo <p style=\"...\">...</p> ", "C: "+" Se puede vincular a un fichero externo con el c??digo <script\n" +
                "language=\"css\" type=\"text/css\" href=\"hojaEstilo.css\"></script>  ", 3,
                Question.DIFFICULTY_MEDIO, Category.CSS3);
        insertQuestion(q22);
        Question q23 = new Question("En CSS, ??qu?? se suele emplear para centrar un contenido horizontalmente?",
                "A:"+" "+ " margin: center;", "B:"+" "+ "float: center;  ", "C: "+"margin: auto; ", 3,
                Question.DIFFICULTY_MEDIO, Category.CSS3);
        insertQuestion(q23);
        Question q24 = new Question("??Cu??l es la sintaxis correcta de CSS para que todos los elementos <p> aparezcan en negrita?",
                "A:"+" "+ "p{text-decoration:bold} ", "B:"+" "+ "p{font-weight:bold} ", "C: "+"Ninguna de las anteriores ", 2,
                Question.DIFFICULTY_MEDIO, Category.CSS3);
        insertQuestion(q24);
        Question q25 = new Question("??Cu??l es la propiedad de CSS que permite cambiar el tama??o del texto?",
                "A:"+" "+ " font-size", "B:"+" "+ "text-size ", "C: "+"text-style", 1,
                Question.DIFFICULTY_MEDIO, Category.CSS3);
        insertQuestion(q25);
        Question q26 = new Question("??C??mo se define un color de fondo para todas las etiquetas <h1> en CSS?",
                "A:"+" "+ "h1.all {background-color:#FFFFFF} ", "B:"+" "+ "h1 {background-color:#FFFFFF}", "C: "+"h1.* {background-color:#FFFFFF} ", 2,
                Question.DIFFICULTY_DIFICIL, Category.CSS3);
        insertQuestion(q26);
        Question q27 = new Question("En CSS, para definir el espacio entre el borde de un elemento y su contenido se emplea la propiedad",
                "A:"+" "+ "padding ", "B:"+" "+ " margin ", "C: "+"border ", 1,
                Question.DIFFICULTY_DIFICIL, Category.CSS3);
        insertQuestion(q27);
        Question q28 = new Question("??C??mo se hace en CSS para que un enlace se muestre sin el subrayado?",
                "A:"+" "+ "a{underline:none}  ", "B:"+" "+ "a{text-decoration:no-underline} ", "C: "+"a{text-decoration:none}", 3,
                Question.DIFFICULTY_DIFICIL, Category.CSS3);
        insertQuestion(q28);
        Question q29 = new Question("??Cu??l de las siguientes propiedades de CSS3 es incorrecta?",
                "A:"+" "+ "transition-duration", "B:"+" "+ "transition-property", "C: "+"transition-animation", 3,
                Question.DIFFICULTY_DIFICIL, Category.CSS3);
        insertQuestion(q29);
        Question q30 = new Question("??C??mo se puede cambiar el margen izquierdo de un elemento con CSS?",
                "A:"+" "+ "text-indent  ", "B:"+" "+ "margin-left ", "C: "+"marginleft  ", 2,
                Question.DIFFICULTY_DIFICIL, Category.CSS3);
        insertQuestion(q30);
        Question q31 = new Question("??El sistema de cuadr??cula Bootstrap se basa en cu??ntas columnas?",
                "A:"+" "+ "8", "B:"+" "+ "12", "C: "+"6", 2,
                Question.DIFFICULTY_FACIL, Category.BOOTSTRAP);
        insertQuestion(q31);
        Question q32 = new Question("??Qu?? clase se usa para crear una caja grande para llamar la atenci??n adicional?",
                "A:"+" "+ ".jumbotron", "B:"+" "+ ".container", "C: "+".bigbox", 1,
                Question.DIFFICULTY_FACIL, Category.BOOTSTRAP);
        insertQuestion(q32);
        Question q33 = new Question("??Qu?? clase se usa para crear una insignia?",
                "A:"+" "+ ".tag", "B:"+" "+ ".label", "C: "+".badge  ", 3,
                Question.DIFFICULTY_FACIL, Category.BOOTSTRAP);
        insertQuestion(q33);
        Question q34 = new Question("??Qu?? complemento se usa para crear una ventana modal?",
                "A:"+" "+ "Modal", "B:"+" "+ "Dialog Box", "C: "+"Popup", 1,
                Question.DIFFICULTY_FACIL, Category.BOOTSTRAP);
        insertQuestion(q34);
        Question q35 = new Question("??Qu?? clase contextual indica una acci??n exitosa o positiva?",
                "A:"+" "+ ".text-success", "B:"+" "+ ".text-warning", "C: "+".text-primary", 1,
                Question.DIFFICULTY_FACIL, Category.BOOTSTRAP);
        insertQuestion(q35);
        Question q36 = new Question("??Qu?? clase contextual indica una acci??n peligrosa o potencialmente negativa?",
                "A:"+" "+ ".text-success", "B:"+" "+ ".text-danger  ", "C: "+".text-primary", 2,
                Question.DIFFICULTY_MEDIO, Category.BOOTSTRAP);
        insertQuestion(q36);
        Question q37 = new Question("??Qu?? clase indica texto en may??sculas?",
                "A:"+" "+ ".uppercase", "B:"+" "+ ".text-capitalize", "C: "+".text-uppercase", 3,
                Question.DIFFICULTY_MEDIO, Category.BOOTSTRAP);
        insertQuestion(q37);
        Question q38 = new Question("El sistema de cuadr??cula Bootstrap funciona en m??ltiples dispositivos.",
                "A:"+" "+ "verdadero", "B:"+" "+ "falso", "C: "+"solo en algunos", 1,
                Question.DIFFICULTY_MEDIO, Category.BOOTSTRAP);
        insertQuestion(q38);
        Question q39 = new Question("??Qu?? complemento se usa para recorrer los elementos, como una presentaci??n de diapositivas?",
                "A:"+" "+ "Carousel ", "B:"+" "+ "Slideshow", "C: "+"Scrollspy", 1,
                Question.DIFFICULTY_MEDIO, Category.BOOTSTRAP);
        insertQuestion(q39);
        Question q40 = new Question("??Qu?? clase se usa para crear una barra de navegaci??n negra?",
                "A:"+" "+ ".navbar-dark ", "B:"+" "+ ".navbar-default", "C: "+".navbar-inverse  ", 3,
                Question.DIFFICULTY_MEDIO, Category.BOOTSTRAP);
        insertQuestion(q40);
        Question q41 = new Question("Se crea una barra de navegaci??n est??ndar con:",
                "A:"+" "+ "<nav class = \"nav navbar\">", "B:"+" "+ "<nav class = \"navbar navbar-default\">  ", "C: "+"<nav class = \"navbar default-navbar\">", 2,
                Question.DIFFICULTY_DIFICIL, Category.BOOTSTRAP);
        insertQuestion(q41);
        Question q42 = new Question("Se crea una pesta??a de navegaci??n est??ndar con:",
                "A:"+" "+ "<ul class = \"nav nav-navbar\"> ", "B:"+" "+ "<ul class = \"navigation-tabs\">", "C: "+"<ul class = \"nav nav-tabs\">  ", 3,
                Question.DIFFICULTY_DIFICIL, Category.BOOTSTRAP);
        insertQuestion(q42);
        Question q43 = new Question("??Qu?? clase indica un men?? desplegable?",
                "A:"+" "+ ".dropdown ", "B:"+" "+ ".select", "C: "+".dropdown-list", 1,
                Question.DIFFICULTY_DIFICIL, Category.BOOTSTRAP);
        insertQuestion(q43);
        Question q44 = new Question("??Qu?? clase agrega un encabezado a un panel?",
                "A:"+" "+ ".panel-head", "B:"+" "+ ".panel-heading  ", "C: "+".panel-header", 2,
                Question.DIFFICULTY_DIFICIL, Category.BOOTSTRAP);
        insertQuestion(q44);
        Question q45 = new Question("??C??mo puedes insertar un icono de b??squeda?",
                "A:"+" "+ "<span class = \"glyphicon glyphicon-search\"> </span>  ", "B:"+" "+ "<span class = \"glyph glyph-search\"> </span>  ", "C: "+"Ninguna de las anteriores", 1,
                Question.DIFFICULTY_DIFICIL, Category.BOOTSTRAP);
        insertQuestion(q45);
        Question q46 = new Question("??Qu?? significa SQL?",
                "A:"+" "+ "Structured Query Language ", "B:"+" "+ "Strong Question Language", "C: "+"Structured Question Language", 1,
                Question.DIFFICULTY_FACIL, Category.MYSQL);
        insertQuestion(q46);
        Question q47 = new Question("??Qu?? instrucci??n SQL se usa para extraer datos de una base de datos?",
                "A:"+" "+ "EXTRACT", "B:"+" "+ "SELECT  ", "C: "+"GET", 2,
                Question.DIFFICULTY_FACIL, Category.MYSQL);
        insertQuestion(q47);
        Question q48 = new Question("??Qu?? instrucci??n SQL se usa para actualizar datos en una base de datos?",
                "A:"+" "+ "SET", "B:"+" "+ "MODIFY", "C: "+"UPDATE ", 3,
                Question.DIFFICULTY_FACIL, Category.MYSQL);
        insertQuestion(q48);
        Question q49 = new Question("??Qu?? instrucci??n SQL se utiliza para eliminar datos de una base de datos?",
                "A:"+" "+ "DELETE ", "B:"+" "+ "REMOVE", "C: "+"COLLAPSE", 1,
                Question.DIFFICULTY_FACIL, Category.MYSQL);
        insertQuestion(q49);
        Question q50 = new Question("??Qu?? instrucci??n SQL se usa para insertar nuevos datos en una base de datos?",
                "A:"+" "+ "INSERT NEW", "B:"+" "+ "INSERT INTO", "C: "+"ADD RECORD", 2,
                Question.DIFFICULTY_FACIL, Category.MYSQL);
        insertQuestion(q50);
        Question q51 = new Question("Con SQL, ??c??mo selecciona una columna llamada \"Nombre\" de una tabla llamada \"Personas\"?",
                "A:"+" "+ "SELECT Personas.Nombre", "B:"+" "+ "SELECT * from Personas.Nombre", "C: "+"SELECT Nombre FROM Personas", 3,
                Question.DIFFICULTY_MEDIO, Category.MYSQL);
        insertQuestion(q51);
        Question q52 = new Question("Con SQL, ??c??mo selecciona todos los registros de una tabla llamada \"Personas\" donde el valor de la columna \"Nombre\" es \"Juan\"?",
                "A:"+" "+ "SELECT * FROM Personas WHERE Nombre='Juan'  ", "B:"+" "+ "SELECT * FROM Personas WHERE Nombre<>'Juan'", "C: "+"SELECT [all] FROM Personas WHERE Nombre LIKE 'Juan'", 1,
                Question.DIFFICULTY_MEDIO, Category.MYSQL);
        insertQuestion(q52);
        Question q53 = new Question("Con SQL, ??c??mo selecciona todos los registros de una tabla llamada \"Personas\" donde el valor de la columna \"Nombre\" comienza con una \"J\"?",
                "A:"+" "+ "SELECT * FROM Personas WHERE Nombre='%J%'", "B:"+" "+ "SELECT * FROM Personas WHERE Nombre LIKE 'J%'", "C: "+"SELECT * FROM Personas WHERE Nombre='J'", 2,
                Question.DIFFICULTY_MEDIO, Category.MYSQL);
        insertQuestion(q53);
        Question q54 = new Question("El operador OR muestra un registro si CUALQUIER condici??n de la lista es verdadera. El operador AND muestra un registro si TODAS las condiciones enumeradas son verdaderas",
                "A:"+" "+ "Verdadero", "B:"+" "+ "Falso", "C: "+"En algunas ocasiones", 1,
                Question.DIFFICULTY_MEDIO, Category.MYSQL);
        insertQuestion(q54);
        Question q55 = new Question("Con SQL, ??c??mo puede insertar \"suarez\" como \"Apellido\" en la tabla \"Personas\"?",
                "A:"+" "+ "INSERT INTO Personas (Apellido) VALUES ('Suarez')", "B:"+" "+ "INSERT ('Suarez') INTO Personas (Apellido)", "C: "+"INSERT INTO Personas ('Suarez') INTO Apellido", 1,
                Question.DIFFICULTY_MEDIO, Category.MYSQL);
        insertQuestion(q55);
        Question q56 = new Question("Con SQL, ??c??mo selecciona todos los registros de una tabla llamada \"Personas\" donde el \"Nombre\" es \"Juan\" y el \"Apellido\" es \"Suarez\"?",
                "A:"+" "+ "SELECT Nombre='Juan', Apellido='Suarez' FROM Personas", "B:"+" "+ "SELECT * FROM Personas WHERE Nombre<>'Juan' AND Apellido<>'Suarez'", "C: "+"SELECT * FROM Personas WHERE Nombre='Juan' AND Apellido='Suarez'", 3,
                Question.DIFFICULTY_DIFICIL, Category.MYSQL);
        insertQuestion(q56);
        Question q57 = new Question("Con SQL, ??c??mo selecciona todos los registros de una tabla llamada \"Personas\" donde el \"Apellido\" est?? alfab??ticamente entre (e incluye) \"Suarez\" y \"Villamil\"?",
                "A:"+" "+ "SELECT Apellido>'Suarez' AND Apellido<'Villamil' FROM Personas", "B:"+" "+ "SELECT * FROM Personas WHERE Apellido BETWEEN 'Suarez' AND 'Villamil'  ", "C: "+"SELECT * FROM Personas WHERE Apellido>'Suarez' AND Apellido<'Villamil'", 2,
                Question.DIFFICULTY_DIFICIL, Category.MYSQL);
        insertQuestion(q57);
        Question q58 = new Question("??Qu?? instrucci??n SQL se usa para devolver solo valores diferentes?",
                "A:"+" "+ "SELECT DIFFERENT", "B:"+" "+ "SELECT DISTINCT  ", "C: "+"SELECT UNIQUE", 2,
                Question.DIFFICULTY_DIFICIL, Category.MYSQL);
        insertQuestion(q58);
        Question q59 = new Question("Con SQL, ??c??mo puede devolver todos los registros de una tabla llamada \"Personas\" ordenadas descendiendientemente por \"Nombre\"?",
                "A:"+" "+ "SELECT * FROM Personsa ORDER BY Nombre DESC  ", "B:"+" "+ "SELECT * FROM Personas SORT 'Nombre' DESC", "C: "+"SELECT * FROM Personas ORDER Nombre DESC", 1,
                Question.DIFFICULTY_DIFICIL, Category.MYSQL);
        insertQuestion(q59);
        Question q60 = new Question("Con SQL, ??c??mo puede insertar un nuevo registro en la tabla \"Personas\"?",
                "A:"+" "+ "INSERT INTO Personas VALUES ('Monica', 'Casas')  ", "B:"+" "+ "INSERT VALUES ('Monica', 'Casas') INTO Personas", "C: "+"INSERT ('Monica', 'Casas') INTO Personas", 1,
                Question.DIFFICULTY_DIFICIL, Category.MYSQL);
        insertQuestion(q60);
        Question q61 = new Question("??Qu?? significa PHP?",
                "A:"+" "+ " Hypertext Preprocessor", "B:"+" "+ "Personal Hypertext Processor", "C: "+"Private Home Page", 1,
                Question.DIFFICULTY_FACIL, Category.PHP);
        insertQuestion(q61);
        Question q62 = new Question("En PHP se puede usar tanto comillas simples ('') como comillas dobles (\"\") para cadenas:",
                "A:"+" "+ "Verdadero", "B:"+" "+ "Falso", "C: "+"En algunos casos", 3,
                Question.DIFFICULTY_FACIL, Category.PHP);
        insertQuestion(q62);
        Question q63 = new Question("??C??mo se escribe \"Hola mundo\" en PHP?",
                "A:"+" "+ " \"Hola Mundo\";", "B:"+" "+ "echo \"Hola mundo\";", "C: "+"printlrn\"Hola Mundo\";", 2,
                Question.DIFFICULTY_FACIL, Category.PHP);
        insertQuestion(q63);
        Question q64 = new Question("??Con que simbolo comienzan todas las variables en PHP?",
                "A:"+" "+ "&", "B:"+" "+ "!", "C: "+"$", 3,
                Question.DIFFICULTY_FACIL, Category.PHP);
        insertQuestion(q64);
        Question q65 = new Question("??Cu??l es la forma correcta de finalizar una declaraci??n PHP?",
                "A:"+" "+ ";", "B:"+" "+ "</php>", "C: "+"new line", 1,
                Question.DIFFICULTY_FACIL, Category.PHP);
        insertQuestion(q65);
        Question q66 = new Question("Los scripts del servidor PHP est??n rodeados de delimitadores, ??cu??l?",
                "A:"+" "+ "<? php ...?>  ", "B:"+" "+ "<? php> ... </?>", "C: "+"<script> ... </script>", 1,
                Question.DIFFICULTY_MEDIO, Category.PHP);
        insertQuestion(q66);
        Question q67 = new Question("La sintaxis de PHP es m??s similar a:",
                "A:"+" "+ "JavaScript ", "B:"+" "+ "Perl y C", "C: "+"CSS3", 2,
                Question.DIFFICULTY_MEDIO, Category.PHP);
        insertQuestion(q67);
        Question q68 = new Question("??C??mo se obtiene informaci??n de un formulario que se env??a utilizando el m??todo \"get\"?",
                "A:"+" "+ "GET [];", "B:"+" "+ "Request.QueryString;", "C: "+"$ _GET [];  ", 3,
                Question.DIFFICULTY_MEDIO, Category.PHP);
        insertQuestion(q68);
        Question q69 = new Question("Cuando se utiliza el m??todo POST, las variables se muestran en la URL:",
                "A:"+" "+ "Verdadero", "B:"+" "+ "Falso", "C: "+"En algunas ocasiones", 2,
                Question.DIFFICULTY_MEDIO, Category.PHP);
        insertQuestion(q69);
        Question q70 = new Question("??Cu??l es la forma correcta de crear una funci??n en PHP?",
                "A:"+" "+ "function myFunction()", "B:"+" "+ "create myFunction()", "C: "+"new_function myFunction()", 1,
                Question.DIFFICULTY_MEDIO, Category.PHP);
        insertQuestion(q70);
        Question q71 = new Question("??Qu?? variable superglobal contiene informaci??n sobre encabezados, rutas y ubicaciones de script?",
                "A:"+" "+ "$_SESSION", "B:"+" "+ "$_GET", "C: "+"$_SERVER  ", 3,
                Question.DIFFICULTY_DIFICIL, Category.PHP);
        insertQuestion(q71);
        Question q72 = new Question("??Cu??l es la forma correcta de agregar 1 a la variable $x?",
                "A:"+" "+ "$x ++;  ", "B:"+" "+ "x++;", "C: "+"$x=+1", 1,
                Question.DIFFICULTY_DIFICIL, Category.PHP);
        insertQuestion(q72);
        Question q73 = new Question("??Cu??l es la forma correcta de agregar un comentario en PHP?",
                "A:"+" "+ "* \\ ... \\ *", "B:"+" "+ "/*...*/  ", "C: "+"<! --...-->", 2,
                Question.DIFFICULTY_DIFICIL, Category.PHP);
        insertQuestion(q73);
        Question q74 = new Question("??Cu??l de estas variables tiene un nombre ilegal?",
                "A:"+" "+ "$ my-Var ", "B:"+" "+ "$ my_Var", "C: "+"$ myVar", 1,
                Question.DIFFICULTY_DIFICIL, Category.PHP);
        insertQuestion(q74);
        Question q75 = new Question("??C??mo se crea una matriz en PHP?",
                "A:"+" "+ "$ car = array [\"Volvo\", \"BMW\", \"Toyota\"]; ", "B:"+" "+ "$ car = \"Volvo\", \"BMW\", \"Toyota\";", "C: "+"$ car = array (\"Volvo\", \"BMW\", \"Toyota\");", 3,
                Question.DIFFICULTY_DIFICIL, Category.PHP);
        insertQuestion(q75);

    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuizContract.QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(QuizContract.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(QuizContract.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuizContract.QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}