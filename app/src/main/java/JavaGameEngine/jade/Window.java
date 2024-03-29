package JavaGameEngine.jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.Callbacks;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL11.*;



public class Window {
  private int width, height;
  private String title;
  private long glfwWindow;

  private static Window window = null;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Mario";

  }

  public static Window get() {
    if (Window.window == null){
      Window.window = new Window();
    }
    return Window.window;
  }

  public void run() {
    System.out.println("Hello LWJGL " + Version.getVersion() + "!");
    init();
    loop();

    //Free memory
    Callbacks.glfwFreeCallbacks(glfwWindow);
    glfwDestroyWindow(glfwWindow);

    //Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  public void init() {
    //setup error callback
    GLFWErrorCallback.createPrint(System.err).set();

    // Init GLFW
    if(!glfwInit()){
      throw new IllegalStateException("Unable to initialize GLFW");
    }
  

    //Configure GLFW
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

    //create the window
    glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
    if(glfwWindow == NULL){
      throw new IllegalStateException("Failed to create the GLFW Window. ");
    }

    //Make the OpenGL context current
    glfwMakeContextCurrent(glfwWindow);
    //enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(glfwWindow);
    //critical for LWJGL's interoperation with GLFW OPENGL
    GL.createCapabilities();
  }
  
  public void loop() {
    while(!glfwWindowShouldClose(glfwWindow)) {
      // Poll events
      glfwPollEvents();
      glClearColor(1.0f,0.0f,0.0f,1.0f);
      glClear(GL_COLOR_BUFFER_BIT);

      glfwSwapBuffers(glfwWindow);

    }
  }
}
