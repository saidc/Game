/*
 * Sample dummy shader to check the highlighter plugin.
 */
#type vertex

#version 430 core
#extension GL_ARB_shading_language_include : require
layout (location = 0 ) in vec3 aPos;
layout (location = 1 ) in vec4 aColor;
out vec4 fColor;
void main(){
    fColor = aColor;
    gl_Position = vec4(aPos,1.0)
}

#type fragment 
#version 430 core
in vec4 fColor;
out vec4 Color;
void main(){
    Color = fColor;
}
