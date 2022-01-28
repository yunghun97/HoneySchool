import http from "./axios.service"

class ArtcileService {
    createArticle(data: any) : Promise<any> {
        return http.post("/board/class", data);
    }
}