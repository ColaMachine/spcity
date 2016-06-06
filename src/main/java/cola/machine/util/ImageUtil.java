package cola.machine.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;

import cola.machine.config.Config;
import cola.machine.config.ImageConfig;
import cola.machine.mng.PathManager;
import cola.machine.util.encrypt.Base64Util;
import core.action.ResultDTO;

/**
 * 二维码生成海报
 * 
 * @author dozen.zhang
 *
 */
public class ImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    private static BufferedImage templateImage;

    /**
     * 图像放大
     * 
     * @param originalImage
     * @param times
     * @return
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, Integer times) {
        int width = originalImage.getWidth() * times;
        int height = originalImage.getHeight() * times;
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 图像放大
     * 
     * @param originalImage
     * @param times
     * @return
     */
    public static BufferedImage fixSize(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 生成海报到系统配置目录 拿两张图片合成
     * 
     * @param smallImage
     * @return
     * @throws IOException
     */
    public static String generatePoster(String smallImage) throws IOException {
        ImageConfig image = Config.getInstance().getImage();
        if (smallImage.startsWith("/") || smallImage.startsWith("\\")) {
            smallImage = smallImage.substring(1);
        }

        try {
            if (templateImage == null) {
                File templatePoster = PathManager.getInstance().getWebRootPath().resolve("poster").resolve("src.jpg")
                        .toFile();
                templateImage = ImageIO.read(templatePoster);
            }

            // 读入小图

            File small = PathManager.getInstance().getImagePath().resolve(smallImage).toFile();

            BufferedImage smallBuffImage = ImageIO.read(small);
            smallBuffImage = zoomInImage(smallBuffImage, 5);
            templateImage.createGraphics().drawImage(smallBuffImage, 500, 800, null);

            // 相对url

            // 文件无后缀名

            // 文件名带后缀名
            if (smallImage.startsWith(image.getQrcodeDir())) {
                smallImage = smallImage.substring(image.getQrcodeDir().length() + 1);
            }

            String fileWithFixx = /*
                                   * DateUtil.formatToString(new Date(),
                                   * DateUtil.YYYYMMDDHHMMSS) +
                                   * RandomString.randomString(4) + ".jpg";
                                   */
            smallImage.replace(image.getQrcodeDir() + "/", "").replace(".gif", ".jpg");// .replace(".gif",
                                                                                       // ".jpg");

            // 实际路径
            Path absolutePath = PathManager.getInstance().getPosterPath().resolve(fileWithFixx);

            File directory = absolutePath.getParent().toFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            ImageIO.write(templateImage, "jpeg", absolutePath.toFile());

            String poster_dir = image.getPosterDir().trim();
            if (poster_dir.startsWith("/"))
                poster_dir = poster_dir.substring(1);
            if (!poster_dir.endsWith("/"))
                poster_dir += "/";

            // 上传文件到服务器
            /*
             * if(image.isServerEnable()) ImageUtil.upload(new String[] {
             * poster_dir + fileWithFixx });
             */
            return poster_dir + fileWithFixx;
        } catch (IOException e) {
            logger.error("generate poster error ", e);
            throw new IOException("generate Poster fail", e);
        }
    }

    /**
     * 在服务器上把要打印的海报打包成zip
     * 
     * @param relatvieFilePaths
     * @return
     * @throws IOException
     */
    /*
     * public static String zipFileOnServer(String[] relatvieFilePaths) throws
     * IOException { logger.debug("begin zipFileOnserver , the param is :" +
     * relatvieFilePaths); ImageConfig image = Config.getInstance().getImage();
     * String user = image.getServerUser(); String pass = image.getServerPwd();
     * String host = image.getServerIp(); String dir = image.getServerDir();
     * Connection con = new Connection(host); try { con.connect(); boolean
     * isAuthed = con.authenticateWithPassword(user, pass);
     * logger.debug("isAuthed====" + isAuthed); // 拼接目录名 String zipfile =
     * "demo.zip";
     * 
     * String serverPath = image.getServerDir(); if (!serverPath.endsWith("/"))
     * { serverPath += "/"; } String poster_zip_dir =
     * image.getPosterZipDir().trim(); if (poster_zip_dir.startsWith("/"))
     * poster_zip_dir = poster_zip_dir.substring(1); if
     * (!poster_zip_dir.endsWith("/")) poster_zip_dir += "/";
     * 
     * String relativePath = poster_zip_dir + zipfile; if
     * (relativePath.startsWith("/")) relativePath = relativePath.substring(1);
     * 
     * String cmd = "rm -f " + serverPath + relativePath+" &&  mkdir -p " +
     * serverPath + poster_zip_dir + " && zip -j " + serverPath + relativePath;
     * for (int i = 0; i < relatvieFilePaths.length; i++) { cmd += " " +
     * serverPath + relatvieFilePaths[i]; }
     * 
     * ch.ethz.ssh2.Session session = con.openSession();
     * 
     * // 尝试创建目录
     * 
     * logger.debug("excute zip cmd on image server: " + cmd);
     * 
     * session.execCommand(cmd); // logger.debug(
     * "Here is some information about the remote host:"); InputStream stdout =
     * new StreamGobbler(session.getStdout()); BufferedReader br = new
     * BufferedReader(new InputStreamReader(stdout)); while (true) { String line
     * = br.readLine(); if (line == null) break; logger.debug(line);
     * 
     * }
     * 
     * Show exit status, if available (otherwise "null") logger.debug(
     * "ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir, 6);
     * session.close();
     * 
     * con.close(); return relativePath; } catch (IOException e) {
     * logger.error("服务器给海报打zip包出错", e); throw e; }
     * 
     * }
     */
    /**
     * 生成海报并打包
     * 
     * @param smallImages
     * @param bigImage
     * @param width
     * @param height
     * @return
     * @throws IOException
     *//*
       * public static String generateHaiBao(String[] smallImages, String
       * bigImage, int width, int height) throws IOException {
       * 
       * Path finalImages[] = new Path[smallImages.length]; try { // 读入大图 File
       * big = new File(bigImage); BufferedImage bigBuffImage =
       * ImageIO.read(big); for (int i = 0; i < smallImages.length; i++) { //
       * 读入小图 File small = new File(smallImages[i]);
       * 
       * BufferedImage smallBuffImage = ImageIO.read(small);
       * 
       * bigBuffImage.createGraphics().drawImage(smallBuffImage, width, height,
       * null); String file = RandomGUIDUtil.generateKey(); finalImages[i] =
       * PathManager.getInstance().getPosterPath().resolve(file + ".jpg");
       * ImageIO.write(bigBuffImage, "jpeg", finalImages[i].toFile()); }
       * 
       * byte[] buffer = new byte[1024]; // 生成的ZIP文件名为Demo.zip Path strZipName =
       * PathManager.getInstance().getPosterZipPath()
       * .resolve(RandomGUIDUtil.generateKey() + ".zip"); ZipOutputStream out =
       * new ZipOutputStream(new FileOutputStream(strZipName.toFile())); //
       * 需要同时下载的两个文件result.txt ，source.txt for (int i = 0; i <
       * finalImages.length; i++) { File file = finalImages[i].toFile();
       * FileInputStream fis = new FileInputStream(file);
       * 
       * out.putNextEntry(new ZipEntry(file.getName()));
       * 
       * int len;
       * 
       * // 读入需要下载的文件的内容，打包到zip文件
       * 
       * while ((len = fis.read(buffer)) > 0) {
       * 
       * out.write(buffer, 0, len);
       * 
       * }
       * 
       * out.closeEntry();
       * 
       * fis.close();
       * 
       * }
       * 
       * out.close();
       * 
       * System.out.println("生成" + strZipName + "成功"); return
       * strZipName.toString(); } catch (IOException e) { // TODO Auto-generated
       * catch block e.printStackTrace(); throw e;
       * 
       * }
       * 
       * }
       */

    /**
     * 上传文件到服务器
     * 
     *
     *            是相对于webroot的路径
     */
    /*
     * public static void upload(String[] filePaths) { ImageConfig image =
     * Config.getInstance().getImage(); if(!image.isServerEnable()) return ;
     * String user = image.getServerUser(); String pass = image.getServerPwd();
     * String host = image.getServerIp(); String dir = image.getServerDir();
     * Connection con = new Connection(host); try { con.connect(); boolean
     * isAuthed = con.authenticateWithPassword(user, pass);
     * System.out.println("isAuthed====" + isAuthed); SCPClient scpClient =
     * con.createSCPClient(); // SFTPv3Client sftpClient = new
     * SFTPv3Client(con); for (int i = 0; i < filePaths.length; i++) { String
     * filePath = filePaths[i]; if (filePath.startsWith("/")) filePath =
     * filePath.substring(1); String localPath =
     * PathManager.getInstance().getWebRootPath().resolve(filePath).toString();
     * int index = filePath.lastIndexOf("/"); String theDir = ""; if (index !=
     * -1) { ch.ethz.ssh2.Session session = con.openSession(); theDir =
     * filePath.substring(0, index); theDir = dir + "/" + theDir;
     * session.execCommand("mkdir -p " + theDir); // logger.debug(
     * "Here is some information about the remote host:"); InputStream stdout =
     * new StreamGobbler(session.getStdout()); BufferedReader br = new
     * BufferedReader(new InputStreamReader(stdout)); while (true) { String line
     * = br.readLine(); if (line == null) break; logger.debug(line);
     * 
     * } Show exit status, if available (otherwise "null") logger.debug(
     * "ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir, 6);
     * logger.info("创建目录+" + theDir); session.close(); logger.debug(
     * "local image file :" + localPath); } scpClient.put(localPath, theDir); //
     * 从本地复制文件到远程目录 } con.close(); } catch (IOException e) {
     * e.printStackTrace(); }
     * 
     * }
     */

    public static boolean compressImg(String inputPath, String outputPath, Integer width, Integer height,
            boolean proportion) {
        try {
            File file = new File(inputPath);
            if (!file.exists()) {
                return false;
            }
            Image img = ImageIO.read(file);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                logger.error("the image( " + inputPath + " )is wrong!");
                return false;
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = width; // 输出的图片宽度
                    newHeight = height; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
                 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(outputPath);
                // 压缩质量 百分比
                float JPEGcompression = 0.7f;
                int dpi = 300;// 分辨率
                saveAsJPEG(dpi, tag, JPEGcompression, out);
                // 关闭输出流

                // JPEGImageEncoder可适用于其他图片类型的转换
                // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                // encoder.encode(tag);
                out.close();
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static boolean compressForFix(String inputPath) {
        try {
            String outpath = inputPath.replaceAll("(\\.[^\\.]*)$", "_min" + "$1");
            Image img = ImageIO.read(new File(inputPath));
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            int newWidth = 0;
            int newHeight = 0;
            if (width > 1000) {
                newWidth = width / 2;
                newHeight = height / 2;
            } else if (width > 500) {
                newWidth = width * 2 / 3;
                newHeight = height * 2 / 3;
            } else {
                newWidth = width;
                newHeight = height;
            }
            return compressImg(inputPath, outpath, newWidth, newHeight, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static void main(String args[]) {
        // try {
        // /*
        // * ImageUtil.generateHaiBao( new String[] {
        // * "C:\\Users\\dozen.zhang\\Pictures\\QQ图片20150729112804.png",
        // * "C:\\Users\\dozen.zhang\\Pictures\\QQ图片20150729112804.png",
        // * "C:\\Users\\dozen.zhang\\Pictures\\QQ图片20150729112804.png" },
        // * "C:\\Users\\dozen.zhang\\Pictures\\qyyygqfjzm_45324.jpg", 300,
        // * 400);
        // */
        // ImageUtil.generatePoster("qrcode/QQ图片20150916161338.gif");
        //
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        long start = System.currentTimeMillis();
        compressForFix("E:\\1.jpg");

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 说明:
     * 
     * @param path
     * @param sufix
     * @param imageName
     * @param imageData
     * @return void
     * @author dozen.zhang
     * @throws IOException
     * @date 2015年12月20日下午12:52:39
     */
    public static ResultDTO saveImage(String path, String imageName, String imageData) throws IOException {
        int success = 0;
        String message = "";
        if (null == imageData || imageData.length() < 100) {
            // 数据太短，明显不合理
            return ResultUtil.getWrongResultFromCfg("err.upload.img.tooshort");
        } else {
            if(imageData.startsWith("%2B")){
                imageData=URLDecoder.decode(imageData, "UTF-8").substring(1);
            }else if(imageData.startsWith("+")){
                imageData=imageData.substring(1);
            }
            imageData=imageData.substring(imageData.indexOf("iVBO"));
            // 去除开头不合理的数据
           // imageData = URLDecoder.decode(imageData, "UTF-8");
            // imageData = imageData.substring(30);
            // int position=imageData.indexOf(",");
            // imageData=imageData.substring(position+1);
            // data:image/jpeg;base64,/9j/4AAQSkZJRgABA
            // data:image/png;base64,iVBORw0KGgoAAAANSUh
            // System.out.println(imageData);
            byte[] data = decode(imageData);
            int len = data.length;
            int len2 = imageData.length();
            if (null == imageName || imageName.length() < 1) {
                imageName = System.currentTimeMillis() + ".png";
            }
            saveImageToDisk(data, path, imageName);
            //
            success = 1;
            message = "上传成功,参数长度:" + len2 + "字符，解析文件大小:" + len + "字节";
        }
        return ResultUtil.getResult(1, imageName, "上传成功", null);
    }

    // references: http://blog.csdn.net/remote_roamer/article/details/2979822
    private static boolean saveImageToDisk(byte[] data, String path, String imageName) throws IOException {
        int len = data.length;
        // 写入到文件
   /*     System.out.println(data);
        for(int i=0;i<data.length;i++){
            System.out.print(data[i]);
        }*/
        FileOutputStream outputStream = new FileOutputStream(new File(path, imageName));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        return true;
    }

    private static byte[] decode(String imageData) throws IOException {
       /* sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        return decoder.decodeBuffer(imageData)*/;

        byte[] data = Base64Util.decodeBuffer(imageData);
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {
                // 调整异常数据

                data[i] += 256;
            }
        }
        return data;
    }

    /**
     * 以JPEG编码保存图片
     * 
     * @param dpi
     *            分辨率
     * @param image_to_save
     *            要处理的图像图片
     * @param JPEGcompression
     *            压缩比
     * @param fos
     *            文件输出流
     * @throws IOException
     */
    public static void saveAsJPEG(Integer dpi, BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos)
            throws IOException {

        // useful documentation at
        // http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
        // useful example program at
        // http://johnbokma.com/java/obtaining-image-metadata.html to output
        // JPEG data

        // old jpeg class
        // com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder =
        // com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
        // com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam =
        // jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);

        // Image writer
        JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        // and metadata
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);

        // if(dpi != null && !dpi.equals("")){
        //
        // //old metadata
        // //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
        // //jpegEncodeParam.setXDensity(dpi);
        // //jpegEncodeParam.setYDensity(dpi);
        //
        // //new metadata
        // Element tree = (Element)
        // imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
        // Element jfif =
        // (Element)tree.getElementsByTagName("app0JFIF").item(0);
        // jfif.setAttribute("Xdensity", Integer.toString(dpi) );
        // jfif.setAttribute("Ydensity", Integer.toString(dpi));
        //
        // }

        if (JPEGcompression >= 0 && JPEGcompression <= 1f) {

            // old compression
            // jpegEncodeParam.setQuality(JPEGcompression,false);

            // new Compression
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(JPEGcompression);

        }

        // old write and clean
        // jpegEncoder.encode(image_to_save, jpegEncodeParam);

        // new Write and clean up
        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
        ios.close();
        imageWriter.dispose();

    }
}
