import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { NavbarItem } from './navbar-item';

describe('NavbarItem', () => {
  let component: NavbarItem;
  let fixture: ComponentFixture<NavbarItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarItem],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarItem);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('name', 'Início');
    fixture.componentRef.setInput('path', '/home');
    fixture.componentRef.setInput('icon', 'home');
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
