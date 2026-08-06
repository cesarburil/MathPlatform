import { Component, input } from '@angular/core';
import { CommentResponse } from '../../../models/CommentResponse';

@Component({
  selector: 'app-comment',
  imports: [],
  templateUrl: './comment.html',
  styleUrl: './comment.scss',
})
export class Comment {

  comment = input<CommentResponse>();
  
}
