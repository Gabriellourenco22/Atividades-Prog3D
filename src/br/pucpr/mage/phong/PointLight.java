package br.pucpr.mage.phong;

import br.pucpr.mage.Shader;
import org.joml.Vector3f;

import java.awt.*;


public class PointLight {
    Vector3f position;
    Vector3f ambientLight;
    Vector3f diffuseLight;
    Vector3f specularLight;


    public PointLight() {
        position = new Vector3f(0.0f, -1.0f, -1.0f);
        ambientLight = new Vector3f(0.0f, 3.0f, 4.0f);
        diffuseLight = new Vector3f(1.0f, 1.0f, 0.8f);
        specularLight = new Vector3f(2.0f, 2.0f, 2.0f);

    }

    public void apply(Shader shader) {
        shader.setUniform("uLightPos", position)
                .setUniform("uAmbientLight", ambientLight)
                .setUniform("uDiffuseLight", diffuseLight)
                .setUniform("uSpecularLight", specularLight);
    }

}
