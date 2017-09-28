package com.wb.framework.commonUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Hex;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

public class WordToHtml
{
    
    public static void main(String argv[])
    {
        try
        {
            FileInputStream f = new FileInputStream(new File("c://bb.doc"));
            ByteArrayOutputStream out = new ByteArrayOutputStream(100);
            byte[] b = new byte[1000];
            int n;
            while ((n = f.read(b)) != -1)
            {
                out.write(b, 0, n);
            }
            String data = Hex.encodeHexString(out.toByteArray());
            byte[] tempDoc = Hex.decodeHex(data.toCharArray());
            f.close();
            out.close();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 读取每个文字样式
     * 
     * @param fileName
     * @throws Exception
     */
    
    public static String getWordAndStyle(InputStream in)
        throws Exception
    {
        HWPFDocument wordDocument = new HWPFDocument(in);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager()
        {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches)
            {
                return "test/" + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null)
        {
            for (int i = 0; i < pics.size(); i++)
            {
                Picture pic = pics.get(i);
                try
                {
                    pic.writeImageContent(new FileOutputStream("D:/test/" + pic.suggestFullFileName()));
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        out.close();
        return new String(out.toByteArray(), "UTF-8").replace("<html>", "").replace("<head>", "").replace("</head>", "").replace("</html>", "").replace("<body class=\"b1 b2\">", "<div class=\"b1 b2\">").replace("</body>", "</div>");
    }
}
