interface BoardArticles {
    [index: number] : number | string,
    id: number,
    category: string,
    title: string,
    content: string,
    user: string,
    date: number,
  }
// type boardType = Array<BoardArticles>;
export default BoardArticles