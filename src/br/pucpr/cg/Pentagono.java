package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;


public class Pentagono implements Scene {

    private Keyboard keyboard = Keyboard.getInstance();
    private int idvertex;
    private int idposicoes;
    private int idbuffer;
    private int shader;


    private static final String	VERTEX_SHADER =
            "#version 330\n" +
                    "in	vec2 aPosition;\n" +
                    "void main(){\n" +
                    "gl_Position = vec4(aPosition, 0.0, 1.0);\n" +
                    "}";

    private static final String	FRAGMENT_SHADER	= "#version	330\n" 	+
            "out vec4 out_color;\n"	+
            "void main(){\n" +
            "out_color = vec4(5.0,	5.0, 0.0, 5.0);\n"	+
            "}";


    private int compileShader(int tiposhader, String codigo)
    {

        int shaderid = glCreateShader(tiposhader);
        glShaderSource(shaderid, codigo);
        glCompileShader(shaderid);


        if(glGetShaderi(shaderid, GL_COMPILE_STATUS) == GL_FALSE)
        {
            throw new RuntimeException("Unable to compile shader." + glGetShaderInfoLog(shader));
        }


        return shaderid;
    }

    private int linkProgram(int... shaders)
    {

        int programa = glCreateProgram();
        for (int shader: shaders)
        {
            glAttachShader(programa, shader);
        }


        glLinkProgram(programa);
        if(glGetProgrami(programa, GL_LINK_STATUS) == GL_FALSE)
        {
            throw new RuntimeException("Unable to link shader." + glGetProgramInfoLog(programa));
        }


        for(int shader : shaders)
        {
            glDetachShader(programa, shader);
            glDeleteShader(shader);
        }


        return 	programa;
    }


    public void init() {
        idvertex = glGenVertexArrays();

        glBindVertexArray(idvertex);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        int indexData[] = new int[] {
                0, 1, 4,
                4, 1, 2,
                2, 3, 4
        };
        float [] vertexData = new float[]{
                00.0f, 00.9f,
                -0.5f, 00.5f,
                -0.4f, -0.4f,
                00.3f, -0.54f,
                00.5f, 00.45f
        };




        FloatBuffer positionBuffer	= BufferUtils.createFloatBuffer(vertexData.length);
        positionBuffer.put(vertexData).flip();

        idposicoes = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, idposicoes);
        glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);


        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indexData.length);
        indexBuffer.put(indexData).flip();
        idbuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idbuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);


        int vertex = compileShader(GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragment= compileShader(GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        shader = linkProgram(vertex, fragment);
    }
    public void draw()
    {
        glClear(GL_COLOR_BUFFER_BIT);
        glBindVertexArray(idvertex);
        glUseProgram(shader);

        int aPosition = glGetAttribLocation(shader, "aPosition");
        glBindBuffer(GL_ARRAY_BUFFER, idposicoes);
        glEnableVertexAttribArray(aPosition);
        glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idbuffer);
        glDrawElements(GL_TRIANGLES, 11, GL_UNSIGNED_INT, 0);



    }
    public void update(float secs) {
        if (keyboard.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
        }
    }

    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new Pentagono()).show();
    }
}
