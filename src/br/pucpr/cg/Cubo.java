package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import br.pucpr.mage.*;
import br.pucpr.mage.Window;
import br.pucpr.mage.phong.Material;
import br.pucpr.mage.phong.PointLight;
import org.joml.Matrix4f;

import org.joml.Vector3f;

import java.awt.*;

// Aqui estão as atividades 5,7,8.

public class Cubo implements Scene {

    private Keyboard keys = Keyboard.getInstance();

    private Mesh mesh;

    private PointLight light = new PointLight();
    private Material material = new Material();
    private float angleX = 0.0f;
    private float angleY = 0.5f;
    private Camera camera = new Camera();

    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        float rotateSpeed = 75.0f;

        camera.setPosition(new Vector3f(0.0f, 2.0f, 2.0f));
        mesh = MeshFactory.createCube();
        material.setDiffuse();
    }

    @Override
    public void update(float secs) {
        float rotateSpeed = 75.0f;
        float speed = 5.0f;
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);

            return;
        }
        if (keys.isDown(GLFW_KEY_W)) {
            camera.moveFront(speed * secs);
        }

        if (keys.isDown(GLFW_KEY_S)) {
            camera.moveFront(-speed * secs);
        }

        if (keys.isDown(GLFW_KEY_C)) {
            camera.strafeRight(speed * secs);
        }

        if (keys.isDown(GLFW_KEY_Z)) {
            camera.strafeLeft(speed * secs);
        }

        if (keys.isDown(GLFW_KEY_LEFT)) {
            angleY += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_RIGHT)) {
            angleY -= Math.toRadians(180) * secs;;
        }

        if (keys.isDown(GLFW_KEY_UP)) {
            angleX += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_DOWN)) {
            angleX -= Math.toRadians(180) * secs;
        }

    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Shader shader = mesh.getShader();
        shader.bind()
                .setUniform("uProjection", camera.getProjectionMatrix())
                .setUniform("uView", camera.getViewMatrix())
                .setUniform("uCameraPosition", camera.getPosition());
        light.apply(shader);
        material.apply(shader);
        shader.unbind();

        mesh.setUniform("uWorld", new Matrix4f().rotateY(angleY).rotateX(angleX));
        mesh.draw();
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new Cubo(), "Cube with lights", 800, 600).show();
    }
}