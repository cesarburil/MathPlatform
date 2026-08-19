import { Component, OnInit, signal } from '@angular/core';
import { CommentsService } from '../../services/comments.service';
import { CommentResponse } from '../../models/CommentResponse';
import { ToastService } from '../../services/toast.service';
import { Comment } from "./comment/comment";

@Component({
  selector: 'app-comments',
  imports: [Comment],
  templateUrl: './comments.html',
  styleUrl: './comments.scss',
})
export class Comments implements OnInit {

  constructor(
    private commentsService: CommentsService,
    private toasts: ToastService,
  ) { }

  comments = signal<CommentResponse[]>([]);

  addComment() {
    if (!this.newCommentTitle().trim()) {
      this.toasts.error('Escreva um título para o tópico.');
      return;
    }
    this.commentsService.addComment({ title: this.newCommentTitle() }).subscribe((result) => {
      this.comments.update((comments) => [...comments, result]);
      this.newCommentTitle.set('');
    });
  }

  ngOnInit(): void {
    this.commentsService.getAll().subscribe((comments) => {
      this.comments.set(comments);
    });
  }

  showModal = signal(false);
  newCommentTitle = signal('');


  closeModal() {
    this.showModal.set(false);
    this.newCommentTitle.set('');
  }


}
