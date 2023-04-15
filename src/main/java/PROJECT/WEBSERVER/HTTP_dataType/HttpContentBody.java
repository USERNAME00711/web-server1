package PROJECT.WEBSERVER.HTTP_dataType;

import java.util.HashMap;

public class HttpContentBody {
    String body=null;

    public void setBody(String body) {
        this.body = body;
    }

    public HttpContentBody(String body) {
        this.body = body;
    }
    public HashMap<String,String> Parameter(String Content_type){
        HashMap<String,String> Post = new HashMap<String,String>();
        if(Content_type.equals(" application/x-www-form-urlencoded"))
        {
            String params[] = body.split("&");
            for(int i = 0 ; i< params.length;i++ )
            {
                String KeysValus[] = body.split("=");
                if(KeysValus[0]!=null)
                {
                    if(KeysValus[1]==null){KeysValus[1]="";}
                    Post.put(KeysValus[0],KeysValus[1]);


                }

            }
        }
        return Post;
    }

    public void print() {
        System.out.println(body);
    }


    public String getBody() {
        return body;
    }
}
